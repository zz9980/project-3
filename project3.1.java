//// Project 3, Teng Lin 2015/11/4.



//////global


Ball c, r, g, b, y;
Buttons reset, rwall, bird, rat;



float cloudx=40, cloudy=30, clouddx=random(1,3) ;                             // cloud variables
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
  
  //creating balls.
  c= new Ball( width/8, height/2, color (255),0,0);                                    // cue ball
  r= new Ball( color (255,0,0), random( middle+25, right), random (top, bottom) );     // red ball
  r.name= "1";
  g= new Ball( color (0,255,0), random( middle+25, right), random (top, bottom) );     // green ball
  g.name= "2";
  b= new Ball( color (0,0,255), random( middle+25, right), random (top, bottom) );     // blue ball
  b.name= "3";
  y= new Ball( color (255,255,0), random( middle+25, right), random (top, bottom) );   // yellow ball 
  y.name= "4";
  
  //creating buttons, and name.
  reset = new Buttons( 70,105,60,30);
  reset.name= "RESET";
  rwall = new Buttons( 140,105,60,30);
  rwall.name= "WALL";
  bird  = new Buttons( 70,145,60,30);
  bird.name= "BIRDS";
  rat   = new Buttons( 140,145,60,30);
  rat.name= "RAT";
  
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
  buttons();
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
  stroke(0,255,0);                            //grass color green.
  strokeWeight(3);
  
  int gx = 10;
   while( gx < width-20) {
    line( gx, height-30 , gx+15, height);
    line( gx+20, height, gx+30, height-30);
    gx=gx+45;                                //spacing between grass
   }
}


void cloud() {
  noStroke();
  float x;
  
  for ( x = cloudx ; x < width; x++) {
    fill(255);
    ellipse(x, cloudy, 60,40);
    x= x+100;
  }
    cloudx = cloudx + clouddx;
    cloudx %= width+(width/50);
   
}


void ball() { 
  noStroke();
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

//click on ball to reset the ball.
void mousePressed() {
  r.mousePressed();
  g.mousePressed();
  b.mousePressed();
  y.mousePressed();
  
  if ( mouseX<100 && mouseX>40 && mouseY<120 && mouseY>90) {
   reset();
  }
  
  if ( mouseX<170 && mouseX>110 && mouseY<120 && mouseY>90) {
   wall=false;
   r.move2();
   g.move2();
   b.move2();
   y.move2();
  }

}

void buttons() {
  reset.show();
  rwall.show();
  bird.show();
  rat.show();
}



class Buttons {
  int mx, my, l, w;              //buttons centerx , centery , length , width.
  String name="";
  
 Buttons( int templ, int tempr, int tempt, int tempb) {
  mx=templ;
  my=tempr;
  l=tempt;
  w=tempb;
}

void show() {
  fill(0,0,0,30);
  rectMode(CENTER);
  rect( mx, my, l, w);
  textSize(14);
  text( name, mx-20, my+5);
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
  if ( by < top || by > bottom )      bdy *= -1;
 }
 void move2() { 
   if (wall=!wall) { 
    middle=left-20;
   }


 
}
 void reset() {
    bx=  random(middle+25, right );
    by=  random( top, bottom );
    bdx=  random( 1,3 );
    bdy=  random( 1,3 );
    wall= true;
    middle= 355;


  }
  boolean hit( float x, float y ) {
    if (  dist( x,y, this.bx,this.by ) < 30 ) return true;
    else return false;
  }
 
 void mousePressed() {
   if (dist ( bx, by, mouseX, mouseY) <30) {
     bx=  random( 390,right );     
     by=  random( top, bottom );
     bdx=  random( 1,3 );    
     bdy=  random( 1,3 );
   }

 }
  
}
