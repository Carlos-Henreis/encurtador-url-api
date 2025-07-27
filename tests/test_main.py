from fastapi.testclient import TestClient
from app.main import app  # troque pelo nome do seu script

client = TestClient(app)

def test_root():
    response = client.get("/")
    assert response.status_code == 200
    assert response.json() == {"message": "Hello World!"}