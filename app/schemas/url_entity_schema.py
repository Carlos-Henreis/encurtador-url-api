from pydantic import BaseModel, HttpUrl
from datetime import datetime
from typing import Optional
import re

def to_camel(string: str) -> str:
    parts = string.split('_')
    return parts[0] + ''.join(word.capitalize() for word in parts[1:])

class CamelModel(BaseModel):
    class Config:
        from_attributes = True
        alias_generator = to_camel       # converte id → id, criado_em → criadoEm
        populate_by_name = True          # aceita snake ou camel na entrada

class UrlEntityCreate(CamelModel):
    """Schema para criar uma nova URL encurtada"""
    url_origem: str

    class Config:
        json_schema_extra = {
            "example": {
                "urlOrigem": "https://www.exemplo.com.br/pagina-muito-longa-com-parametros?param1=valor1&param2=valor2"
            }
        }

class UrlEntityResponse(CamelModel):
    """Schema de resposta para URL criada"""
    id: int
    url_origem: str
    url_encurtada: str
    criado_em: datetime
    ultimo_acesso_em: Optional[datetime] = None
    total_acessos: int

    class Config:
        from_attributes = True
        json_schema_extra = {
            "example": {
                "id": 1,
                "urlOrigem": "https://www.exemplo.com.br/pagina-muito-longa",
                "urlEncurtada": "abc123",
                "criadoEm": "2025-08-04T12:00:00",
                "ultimoAcessoEm": "2025-08-04T14:30:00",
                "totalAcessos": 5
            }
        }

class UrlEntityStats(CamelModel):
    """Schema para estatísticas da URL"""
    id: int
    url_origem: str
    url_encurtada: str
    criado_em: datetime
    ultimo_acesso_em: Optional[datetime] = None
    total_acessos: int

    class Config:
        from_attributes = True
        json_schema_extra = {
            "example": {
                "id": 1,
                "urlOrigem": "https://www.exemplo.com.br/pagina-muito-longa",
                "urlEncurtada": "abc123",
                "criadoEm": "2025-08-04T12:00:00",
                "ultimoAcessoEm": "2025-08-04T14:30:00",
                "totalAcessos": 15
            }
        }