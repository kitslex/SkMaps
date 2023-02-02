# SkMaps
SkMaps, is a skript addon ive made

Looking for contributions:

I need contributions
please fork it and make pull requests!


Docs: (temp) (Untill I get on skUnity/skHub)

create [a] [new] map (named|with name) %string% with [map] [[with] background [color|colour] %-color%] 
(eg. create new map named "bob" with background colour rgb(255,255,255))

remove [map] %string% %-boolean% 
%-boolean% is if you want to delete the file
(eg. remove "bob" true)

set (background|full) [colour|color] of [map] %string% to %color%
(eg. set background colour of map "bob" to rgb(255,255,255))

draw circle at %number%,[ ]%number% with radius %number% with [color|colour] %color% on [map] [named|with name] %string%
(eg. draw circle at 64,64 with radius 5 with colour rgb(20,20,20) on map named "bob")

draw line (from|between) %number%,[ ]%number%( to | )number%,[ ]%number% with [color|colour] %color% on [map] [named|with name] %string%
(eg. draw line from 1,1 to 127,127 with color rgb(20,20,20) on map named "bob")

draw pixel [at] %number%,[ ]%number% with [color|colour] %color% on [map] [named|with name] %string%
(eg. draw pixel at 1,1 with color rgb(20,20,20) on map named "bob")

[the] map with name %string%
(eg. map with name "bob") returns the map item
