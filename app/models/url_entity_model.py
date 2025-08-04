from sqlalchemy import Column, BigInteger, String, DateTime
from sqlalchemy.sql import func
from app.database import Base

class UrlEntity(Base):
    __tablename__ = "url_entity"
    
    id = Column(BigInteger, primary_key=True, autoincrement=True)
    url_origem = Column(String(255), nullable=False)
    url_encurtada = Column(String(255), unique=True, nullable=False)
    criado_em = Column(DateTime(timezone=True), server_default=func.now(), nullable=False)
    ultimo_acesso_em = Column(DateTime(timezone=True), nullable=True)
    total_acessos = Column(BigInteger, nullable=True, default=0)