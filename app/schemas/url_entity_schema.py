from pydantic import BaseModel, HttpUrl
from datetime import datetime
from typing import Optional

class UrlEntityCreate(BaseModel):
    """Schema para criar uma nova URL encurtada"""
    urlOrigem: str

    class Config:
        json_schema_extra = {
            "example": {
                "urlOrigem": "https://www.exemplo.com.br/pagina-muito-longa-com-parametros?param1=valor1&param2=valor2"
            }
        }

class UrlEntityResponse(BaseModel):
    """Schema de resposta para URL criada"""
    id: int
    urlOrigem: str
    urlEncurtada: str
    criadoEm: datetime
    ultimoAcessoEm: Optional[datetime] = None
    totalAcessos: int

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

class UrlEntityStats(BaseModel):
    """Schema para estatísticas da URL"""
    id: int
    urlOrigem: str
    urlEncurtada: str
    criadoEm: datetime
    ultimoAcessoEm: Optional[datetime] = None
    totalAcessos: int

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