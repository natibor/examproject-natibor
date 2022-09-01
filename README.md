# Munkaidő nyilvántartás applikáció

Az applikáció célja, hogy nyilvántartsa a dolgozókat és projekteket, valamint rögzítse a munkávál töltött időt.

## Entitások

### Dolgozók - Employee

A dolgozókról a következő adatokat tároljuk az adatbázisban:

    - név
    - státusz
    - projektek listája

### Projektek - Project

A projektekről az alábbi információkat rögzítjük:

    - név
    - leírás
    - státusz

### Munkamenet - WorkSession

Egy munkamenet az adatbázisban így kerül mentésre:

    - dolgozó azonosítószáma
    - projekt azonosítószáma
    - könyvelt órák száma

## Végpontok

### Dolgozók - /api/employees

    - dolgozó létrehozása
    - dolgozó megjelenítése azonosító alapján
    - aktív státuszú dolgozók listázása
    - dolgozó "törlése" azonosító alapján (státuszt állít)

### Projektek - /api/projects

    - projekt létrehozása
    - projekt megjelenítése azonosító alapján
    - projektek listázása
    - projekt "törlése" azonosító alapján (státuszt állít)

### Munkamenetek - /api/worksessions

    - munkamenet létrehozása
    - munkamenet megjelenítése azonosító alapján
    - munkamenetek listázása
    - munkamenet módosítása azonosító alapján
    - munkamenet ténylegese törlése azonosító alapján



## Docker parancsok

Adatbázis konténer létrehozása és indítása:
docker run --name worktimedb --network worktimenetwork -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=worktime -d -p 3307:3306 mysql:latest

Alkalmazás image létrehozása:
docker build -t worktimeapp .

Alkalmazás konténer létrehozása és indítása:
docker run --name worktimeapp --network worktimenetwork -p 8080:8080 -d worktimeapp
