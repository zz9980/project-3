//// Project 3, Teng Lin 2015/11/4.

Cloud c1;
Ball c, r, g, b, y;


int tableRed=70, tableGreen=240, tableBlue=230;                               // pool table color
float left=50, right=450, top=100, bottom=250;                                // Table boundaries
float middle=250;
boolean wall=true;

void setup() {
  size( 700, 500 );                                                           //window size
  left=   50;
  right=  width-50;
  top=    100;
  bottom= height-80;
  middle= left + (right-left) / 2;
  
  c1 = new Cloud(40);
  c= new Ball( width/8, height/2, color (255),0,0);                                    // cue ball
  c.name= "0";
  r= new Ball( color (255,0,0), random( middle+25, right), random (top, bottom) );     // red ball
  r.name= "1";
  g= new Ball( color (0,255,0), random( middle+25, right), random (top, bottom) );     // green ball
  g.name= "2";
  b= new Ball( color (0,0,255), random( middle+25, right), random (top, bottom) );     // blue ball
  b.name= "3";
  y= new Ball( color (255,255,0), random( middle+25, right), random (top, bottom) );   // yellow ball 
  y.name= "4";

  reset();

}


void reset() {
  r.reset();
  g.reset();
  b.reset();
  y.reset();
  
}
  

    
void draw() {
  background( 250,250,200 );                                                 //background color
  table( left, top, right, bottom );
  grass();
  cloud();
  ball();

}


void table( float east, float north, float west, float south ) {
  rectMode( CORNERS );
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
  
  int gx = 10;
   while( gx < width-20) {
    line( gx, height-30 , gx+15, height);
    line( gx+20, height, gx+30, height-30);
    gx=gx+45;                               //spacing between grass
   }
}


void cloud() {
  for ( int x = 10; x < width; x++) {
    c1 = new Cloud(40+x);
    x = x+100;
    c1.show();
  } 
}


void ball() { 
  c.show();
  r.show();
  g.show();
  b.show();
  y.show();
  
  c.move();
  r.move();
  g.move();
  b.move();
  y.move();
  
  //// Elastic collisions.
  collision( c,r);
  collision( c,g);
  collision( c,b);
  collision( c,y);
  
  collision( r,g);
  collision( r,b);
  collision( r,y);
  
  collision( g,b);
  collision( g,y);
 
  collision( b,y);
 
  
 
}
void collision( Ball p, Ball q ) {
 if ( p.hit( q.bx,q.by ) ) {
    float tmp;
    tmp=p.bdx;  p.bdx=q.bdx;  q.bdx=tmp;      // Swap the velocities.
    tmp=p.bdy;  p.bdy=q.bdy;  q.bdy=tmp;
    p.move();  p.move();   p.move();
    q.move();  q.move();   q.move();
  }
}

//// HANDLERS:  keys, click
void keyPressed() {
  if( key == 'r') {
    reset();
  }

}










class Cloud {
  int c=255;                    //color
  float cx=40, cy=30;           //positions x, y.
  float cs=1;                   //speed
  
  
 Cloud( float tempx) {
   cx = tempx;
 }

 void show() {
  noStroke();
  fill(c);
  ellipse(cx, cy, 60, 40);
  cx = cx + cs;
 }

}


class Ball {
  int c;
  float bx, by;
  float bdx=random(1,3), bdy=random(1,3);
  String name="";
  
Ball( float tempx, float tempy, color tempc, float tempdx, float tempdy) {
  c=tempc;
  bx=tempx;
  by=tempy;
  bdy=tempdy;
  bdx=tempdx;
  
} 
Ball( color tempc, float tempx, float tempy) {
  c=tempc;
  bx=tempx;
  by=tempy;
 
}
  
 void show() {
   fill(c);
   ellipse( bx, by, 30,30);
   fill(0);
   textSize(15);
   text( name, bx-5, by+5);
   
 }
 
 void move() {
  bx = bx + bdx;
  by = by + bdy;
  // bounce off wall
  if ( bx < middle+25 || bx > right ) bdx *= -1;
  if ( by < top || by > bottom ) bdy *= -1;
  
 }
 
 void reset() {
    bx=  random(middle+25, right );
    by=  random( top, bottom );
    bdx=  random( 1,3 );
    bdy=  random( 1,3 );
 }
  boolean hit( float x, float y ) {
    if (  dist( x,y, this.bx,this.by ) < 30 ) return true;
    else return false;
  }


}

  
