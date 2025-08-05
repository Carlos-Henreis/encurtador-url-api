from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from app.database import engine, Base
from app.routers import urls
from app.middleware import rate_limit_middleware
from dotenv import load_dotenv
import os

# Carrega variáveis de ambiente
load_dotenv()

ALLOW_ORIGINS = os.getenv("ALLOW_ORIGINS")

# Criar tabelas no banco de dados
Base.metadata.create_all(bind=engine)

# Inicializar aplicação FastAPI
app = FastAPI(
    title="URL Shortener API",
    description="API para encurtamento e gerenciamento de URLs",
    version="1.0.0"
)

app.middleware("http")(rate_limit_middleware)

# Configurar CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=["https://encurtadorurl.cahenre.com.br"],  # Em produção, especificar domínios específicos
    allow_credentials=True,
    allow_methods=["GET", "POST", "PUT", "DELETE", "OPTIONS"],
    allow_headers=["*"],
)

# Incluir routers
app.include_router(urls.router)

@app.get("/")
def read_root():
    return {"message": "URL Shortener API está funcionando!"}

@app.get("/health")
def health_check():
    return {"status": "healthy"}