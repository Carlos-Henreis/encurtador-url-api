from pydantic import BaseModel, HttpUrl
from datetime import datetime
from typing import Optional

class UrlEntityCreate(BaseModel):
    """Schema para criar uma nova URL encurtada"""
    url_origem: str

    class Config:
        json_schema_extra = {
            "example": {
                "url_origem": "https://www.exemplo.com.br/pagina-muito-longa-com-parametros?param1=valor1&param2=valor2"
            }
        }

class UrlEntityResponse(BaseModel):
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
                "url_origem": "https://www.exemplo.com.br/pagina-muito-longa",
                "url_encurtada": "abc123",
                "criado_em": "2025-08-04T12:00:00",
                "ultimo_acesso_em": "2025-08-04T14:30:00",
                "total_acessos": 5
            }
        }

class UrlEntityStats(BaseModel):
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
                "url_origem": "https://www.exemplo.com.br/pagina-muito-longa",
                "url_encurtada": "abc123",
                "criado_em": "2025-08-04T12:00:00",
                "ultimo_acesso_em": "2025-08-04T14:30:00",
                "total_acessos": 15
            }
        }