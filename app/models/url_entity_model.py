from sqlalchemy import Column, BigInteger, String, DateTime
from sqlalchemy.sql import func
from app.database import Base

class UrlEntity(Base):
    __tablename__ = "url_entity"
    
    id = Column(BigInteger, primary_key=True, autoincrement=True)
    urlOrigem = Column(String(255), nullable=False)
    urlEncurtada = Column(String(255), unique=True, nullable=False)
    criadoEm = Column(DateTime(timezone=True), server_default=func.now(), nullable=False)
    ultimoAcessoEm = Column(DateTime(timezone=True), nullable=True)
    totalAcessos = Column(BigInteger, nullable=True, default=0)