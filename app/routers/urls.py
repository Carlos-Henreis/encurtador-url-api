from fastapi import APIRouter, Depends, HTTPException, status
from fastapi.responses import RedirectResponse
from sqlalchemy.orm import Session
from sqlalchemy import func
from typing import List, Optional

from app.database import get_db
from app.models.url_entity_model import UrlEntity
from app.schemas.url_entity_schema import UrlEntityCreate, UrlEntityResponse, UrlEntityStats

import secrets
import string
from datetime import datetime

router = APIRouter()

def generate_short_code(length: int = 6) -> str:
    """
    Gera um código curto aleatório usando letras e números
    """
    characters = string.ascii_letters + string.digits
    return ''.join(secrets.choice(characters) for _ in range(length))

@router.post("/", response_model=UrlEntityResponse, status_code=status.HTTP_201_CREATED)
def create_short_link(url_data: UrlEntityCreate, db: Session = Depends(get_db)):
    """
    POST / → Criar link curto
    Recebe uma URL original e retorna um link encurtado
    """
    # Gerar código curto único
    short_code = generate_short_code()

    # Verificar se o código já existe (muito improvável, mas por segurança)
    while db.query(UrlEntity).filter(UrlEntity.url_encurtada == short_code).first():
        short_code = generate_short_code()

    # Criar nova entrada no banco
    db_url = UrlEntity(
        url_origem=url_data.url_origem,
        url_encurtada=short_code,
        criado_em=datetime.now(),
        total_acessos=0
    )

    db.add(db_url)
    db.commit()
    db.refresh(db_url)

    return db_url

@router.get("/{short_code}")
def redirect_to_original(short_code: str, db: Session = Depends(get_db)):
    """
    GET /{shortCode} → Redirecionar para a URL original
    Redireciona para a URL original e incrementa contador de acessos
    """
    # Buscar URL pelo código curto
    url = db.query(UrlEntity).filter(UrlEntity.url_encurtada == short_code).first()

    if not url:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Link não encontrado"
        )

    # Incrementar contador de acessos e atualizar último acesso
    url.total_acessos += 1
    url.ultimo_acesso_em = datetime.now()
    print(f"Redirecionando para: {url.url_origem} (Acessos: {url.total_acessos})")

    db.commit()

    # Redirecionar para URL original (301 = redirecionamento permanente)
    headers = {
        "Cache-Control": "no-cache, no-store, must-revalidate",
        "Pragma": "no-cache",
        "Expires": "0"
    }
    return RedirectResponse(url=url.url_origem, status_code=301, headers=headers)

@router.get("/stats/{short_code}", response_model=UrlEntityStats)
def get_url_statistics(short_code: str, db: Session = Depends(get_db)):
    """
    GET /stats/{shortCode} → Obter estatísticas
    Retorna estatísticas de uso do link encurtado
    """
    # Buscar URL pelo código curto
    url = db.query(UrlEntity).filter(UrlEntity.url_encurtada == short_code).first()

    if not url:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Link não encontrado"
        )

    return UrlEntityStats(
        id=url.id,
        url_origem=url.url_origem,
        url_encurtada=url.url_encurtada,
        criado_em=url.criado_em,
        ultimo_acesso_em=url.ultimo_acesso_em,
        total_acessos=url.total_acessos
    )