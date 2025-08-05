import httpx
from fastapi import Request, HTTPException, status, Header, Depends
from dotenv import load_dotenv
import os

# Carrega variáveis de ambiente
load_dotenv()

RECAPTCHA_SECRET_KEY = os.getenv("RECAPTCHA_SECRET_KEY")

async def validate_recaptcha(x_recaptcha_token: str = Header(...)):
    async with httpx.AsyncClient() as client:
        response = await client.post(
            "https://www.google.com/recaptcha/api/siteverify",
            data={"secret": RECAPTCHA_SECRET_KEY, "response": x_recaptcha_token},
            headers={"Content-Type": "application/x-www-form-urlencoded"}
        )
        result = response.json()
        print(f"reCAPTCHA validation result: {result}")