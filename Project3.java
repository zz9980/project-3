//// Project 3, Teng Lin 2015/11/4.

int tableRed=0, tableGreen=255, tableBlue=65;      // pool table color
float left=50, right=450, top=100, bottom=250;     // Table boundaries
float middle=250;
boolean wall=true;

void setup() {
  size( 700, 500 );                               //window size
  left=   50;
  right=  width-50;
  top=    100;
  bottom= height-80;
  middle= left + (right-left) / 2;
 
}
    
void draw() {
  background( 250,250,200 );
  rectMode( CORNERS );
  table( left, top, right, bottom );
  grass();
  cloud();
}


void table( float east, float north, float west, float south ) {
  fill( tableRed, tableGreen, tableBlue );    // pool table
  strokeWeight(20);
  stroke( 240, 150, 50 );   
  rect( east-20, north-20, west+20, south+20 );
  
  // Start with a WALL down the middle of the table! 
  if (wall) {
    float middle=  (east+west)/2;    
    stroke(0,0,0,30);
    line( middle,north+10, middle,south-10 );
  }
  stroke(0);
  strokeWeight(1);
 
}

void grass() {
  stroke(0,255,0);                           //grass color green.
  strokeWeight(2);
  
  for ( int gx=0; gx < width; gx++) {
    line( gx, 470 , gx+15, 500);
    line( gx, 500,  gx, 470);
    gx=gx+15;
  }
}

void cloud(){
  noStroke();
  fill(255);
  
  int k= frameCount/30%2;
  
    if (k==0) {
     for ( int cx=50; cx < width; cx++, cx= cx+80) {
     ellipse( cx, 30, 65, 50);
    }
   }
  
   else {
    for ( int cx=0; cx < width; cx++, cx= cx+80) {
    ellipse( cx, 30, 70, 55);
   }
  }

}
