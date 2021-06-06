main: ![](https://api.travis-ci.com/kapchx/alkfejl-eventcenter.svg?token=yK894ErRySpzzmBVJwJG)
# alkfejl-eventcenter
Célunk egy webes eseménykezelő alkalmazás létrehozása, mellyel események szervezhetők, és azokra jelentkezni lehet. (Pl. délutáni focihoz csapat létrehozása, csoportos jegy a Korona Fesztiválra, stb.)  
Az eseményekhez kommentek fűzhetők, segítve a szervező, illetve a résztvevők közötti kommunikációt. Egy esemény akár több helyszínt és érinthet, így szélesebb körű események (pl. egyetemi vegán-nap) szervezsére is használható

## Funkiconális követelmények

-  A főoldalon egy lista formájában megtekenthetők a létező események, illetve lehetőség van keresni cím vagy helyszín alapján.  
Egy adott eseményre kattintva megtekinthetők annak helyadatati, résztvevői, részletes leírása és a hozzá füzőtt kommentek.  
Itt hozható létre új esemény is
- A felhasználóknak regisztrálnia kell az oldalon, mielőtt eseményekre jelentkezik vagy hoz létre.
- Minden eseménynek egy tulajdonosa van, viszont a felhasználók akárhány eseményre jelentkezhetnek.
- Az esemény időpontja, helyszíne modóosítható, illetve az esemény törölhető is (a tulajdonos által).
- Egy esemény tulajdonosa eldöntheti, kiknek a jelentkezését fogadja el, illetve kitilthat tagokat az eseményről. Állíthat be létszámkorlátot is.
- Az eseményekhez bárki fűzhet kommentet

## Nem funkcionális követelmények

- Java Spring Boot
- REST API
- Angular

Az adatbázis várható táblái:
- felhasználók
- jelentkezések
- események 
- helyszínek

## Szakterületi fogalomjegyzék
	
- esemény  
Egy vagy több helyszínhez kötött csoportos tevékenység, melyre résztvevők jelentkezhetnek.
- szervező  
Az esemény tulajdonosa aki azt menedzseli, a jelentkezéseket bírálja.
- résztvevő  
Az eseményre jelentkezett felhasználók, akik részt vehetnek azon.
- jelentkezés  
A felhasználó igénye, hogy egy eseményen részt vehessen, mely elutasítható.

## Szerepkörök

- admin  
Az alkalmazás üzemeltetéséhez használható szerepkör
- szervező  
Egy adott eseményhez tartozó, azt létrehozó regisztrált felhasználó
- résztvevő  
Eseményekhez csatlakozó felhasználók

## A projekt későbbi bővítétésre ötletek:

- Értesítések küldése közelgő eseményről
