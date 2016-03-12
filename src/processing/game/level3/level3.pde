/**
* skew tile
*
* @author aa_debdeb
* @date 2016/03/07
*/
 
void setup(){
  size(1068, 600);
  noLoop();
  background(255);
  stroke(0);
  strokeWeight(10);
   
  int step = 25;
  for(int w = 0; w < width; w += step){
    for(int h = 0; h < height; h += step){
      if(random(1) < 0.5){
        line(w, h, w + step, h + step);
      } else {
        line(w + step, h, w, h + step);
      }
    }
  }
}
