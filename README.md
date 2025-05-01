Aplikace zaznamenává souřadnice kliknutí myší na plátně.
Podle toho v jakém "módu" se zrovna nachází umí vykreslit buď čáru nebo obrazec, který dopočítá z daných 2 bodů čáry.

Uživatel může měnit módy pomocí tlačítek nad plátnem. Po kliknutí na tlačítko program vypne módy, které jsou zapnuté a vzájemně se s novým vylučují:

např. pokud uživatel kreslil kružnici a klikne na čtverec, program vypne mód kružnice

pokud uživatel kreslil tečkovanou čáru a klikne na tlačítko rovné čáry, je možné nakreslit rovnou tečkovanou čáry


Další tlačitka jsou:

Clear - vyčistí plátno a body, které si program pamatuje (začíná od znova)

Color - nechá uživatele vybrat barvu, kterou si program uloží do proměnné a na všechno pak používá tuhle barvu

Circle - podle čáry, kterou uživatel nakreslí program dopočítá kruh (uživatel vlastně zadá střed a poloměr)

Square - podle čáry, kterou uživatel nakreslí program dopočítá čtverec (uživatel zadává úhlopříčku)

Rectangle - podle čáry, kterou uživatel nakreslí program dopočítá obdelník (uživatel zadává úhlopříčku)

Polygon - zachytává body, na které uživatel klikl a kreslí mezi nimi čáry, jakmile uživatel vypne tento mód, spojí poslední bod s prvním a tím polygon uzavře

Change Line Width - kolem původní čáry zadané uživatelem nakreslí 2 další a tím čáru rozšíří

Dotted Line - bude považovat čáru za čárkovanou - při vykreslování použije DottedLineRasterizer a čáru si uloží v LineCanvas do listu dottedLines

Dashed Line - bude považovat čáru za čárkovanou - při vykreslování použije DashedLineRasterizer a čáru si uloží v LineCanvas do listu dashedLines

Shift - přepočítá body tak, aby čára byla buď pod úhlem 90 nebo 45 stupňů

Fill - uloží si bod, kam uživatel klikl, aby se fill dal vykreslit znovu a projde všechny pixely v obrazci a změní jim barvu

Delete - najde nejbližší čáru a smaže si ji z listu čar a celé překreslí, aby čára zmizela



Uživatel také může měnit existující čáry pravým tlačítkem myši. V tom případě program najde nejbližší bod, od bodu kam uživatel klikl a uživatel může daný bod "překreslit" jinam.


LineCanvas si ukládá čáry do listu podle toho zda byla normální, tečkovaná, nebo čárkovaná, aby bylo možné čáry následně upravit.

DashedLineRasterizer a DottedLineRasterizer vypočítávají mezery v čáře, aby bylo možné vynechat nějaké pixely při kreslení čáry a tím ji udělat čárkovanou nebo tečkovanou.

BasicFiller si ukládá list pixelů, na které uživatel klikl při vybarvování. Když je potřeba překreslit plátno, projde všechny tyto pixely a udělá vybarvení znova.



metody:

paintCanvasSoFar()
-vykreslí plátno, podle toho co je uložené v listech

initToolBar()
-přidá tlačítka nad plátno

switchFillMode(), switchWidthLineMode(), ...
-zapne daný mód a vypne ostatní, které se s tímto přímo vylučují

createAdapters()
-umožní uživateli zadávat vstupy myší a klávesnicí, podle toho vyhodnocuje co se bude dít dál

adjustForShift(Point currentPoint)
-dopočítá druhý bod, aby bylo možné vykreslit čáru pod úhlem 90 nebo 45 stupňů

searchClosestPoint(int mouseX, int mouseY)
-podle toho, kam uživatel klikne prohledá okolí a vrátí bod, pokud nějaký najde

private void drawPolygonLine()
-vykreslí 1 čáru polygonu
-musí se zavolat několikrát pro celý polygon

drawCircle(Point center, Point edge)
-podle středu a poloměru dopočítá pixely kruhu

drawSquare(Point p1, Point p2)
-podle úhlopříčky dopočítá čáry čtverce

drawRectangle(Point p1, Point p2)
-podle úhlopříčky dopočítá čáry obdelníku

createParallelLines(Line line, int offset)
-vrátí čáry hned vedle té původní - aby bylo možné nakreslit širší čáru

searchClosestLine(int mouseX, int mouseY)
-stejně jako s bodem, ale vrací čáru
