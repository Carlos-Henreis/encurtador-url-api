# Dependências
from fastapi import Request
from starlette.responses import JSONResponse
from time import time
from collections import defaultdict

RATE_LIMIT = 50        # Requisições por janela
WINDOW_SECONDS = 5*60   # Janela em segundos (5 min)
requests_per_ip = defaultdict(list)

def is_rate_limited(ip, path, method):
    now = time()
    window_start = now - WINDOW_SECONDS
    # Limite apenas para as rotas específicas
    if (method == "GET" and path.startswith("/stats/")) or (method == "POST" and path == "/"):
        reqs = [t for t in requests_per_ip[ip] if t > window_start]
        if len(reqs) >= RATE_LIMIT:
            return True
        reqs.append(now)
        requests_per_ip[ip] = reqs
    return False

async def rate_limit_middleware(request: Request, call_next):
    ip = request.client.host
    if is_rate_limited(ip, request.url.path, request.method):
        return JSONResponse({"detail": "Rate limit exceeded"}, status_code=429)
    return await call_next(request)
