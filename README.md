# DogwaterMark
create fake client watermarks for the goofs and laughs. this is a forge mod for minecraft version 1.12.2 and it allows you to easily create watermarks with any text, color and alignment. font and text size currently cannot be specified directly, so this uses the default minecraft font.

## Usage
usage: 
```
/dwm <mode> <params>
```
possible modes are:
- text => change text (any form of text, spaces are fine)
- color => change color (use hex code here)
- align => change alignment (possible are: top_right, bottom_right, top_left, bottom_left)
- shadow => toggle the text shadow (true or false)

example for a **red** watermark, **with** shadow, aligned to the **bottom right** of the screen
```
/dwm text DogwaterClient v4.2.0
/dwm color #ff0000
/dwm align bottom_right
/dwm shadow true
```
to hide the watermark, simply use `/dwm text`, which makes the watermark have empty text.
