# Munkaidő nyilvántartás applikáció

Az applikáció célja, hogy nyilvántartsa a dolgozókat és projekteket, valamint rögzítse a munkávál töltött időt.

## Főbb egységek

### Dolgozók - Employee

A dolgozókról a következő adatokat tároljuk a rendszerben: név, munkával töltött órák száma és munkanapok száma.

### Projektek - Project

A projektekről az alábbi információkat rögzítjük: név, leírás

### Munkamenet - WorkSession




## Végpontok





## Docker parancsok

Adatbázis konténer létrehozása és indítása:
docker run --name WorkTimeAppDB --network worktimenetwork -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=worktime -d -p 3307:3306 mysql:latest

Alkalmazás image létrehozása:
docker build -t worktimeapp .

Alkalmazás konténer létrehozása és indítása:
docker run --name WorkTimeApp --network worktimenetwork -p 8080:8080 -d worktimeapp tail -f /dev/null
