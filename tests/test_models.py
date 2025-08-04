from sqlalchemy import select

from app.db_models import UrlEntity

def test_url_entity():
    # Create an instance of UrlEntity
    url_entity = UrlEntity(
        url="https://example.com",
        short_url="exmpl",
        ultimoAcessoEm="2023-10-01T00:00:00",
        totalAcessos=0
    )

    print(url_entity)

    # Check if the instance is created correctly
    assert url_entity.id is None
    assert url_entity.url == "https://example.com"
    assert url_entity.short_url == "exmpl"
    assert url_entity.totalAcessos == 0