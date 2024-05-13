## Alkalmazás konfigurálása:
Az alkalmazáshoz készült egy `docker-compose.yml` és egy `Dockerfile` ezért az alkalmazás teszteléséhez csak Docker Desktop szükséges.
Én Windows 10 os-n 'Docker version 26.1.1, build 4cf5afa' használatával sikeresen futtattam a következő paranccsal:

`docker-compose up --build -d`

## Teszt adatok:
A resources mappában található data.sql file 11 Advertiser-t (user-t) és 100 hirdetést hoz létre az alkalmazás indításakor. 
email:`test1@test.com`
pw: `Password1`

## Továbbfejlesztési ötletek:
- Az Advertiser ne minden adata menjen ki egy-egy Response-ban (pl. pwd)
- Jelszó és refreshToken hash-elése salt-al (Ezt a könnyebb tesztelhetőség miatt kikommenteltem)
- HTTPS kapcsolat kialakítása
- Logolás kialakítása file-ba vagy db-be
- Observability kialakítása Spring Boot Actuator segítségével
- Cert-ek kiszervezése más forrásba
- DB ne docker-ben fusson
