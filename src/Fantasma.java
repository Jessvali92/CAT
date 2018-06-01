import java.awt.List;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import org.omg.Messaging.SyncScopeHelper;

public class Fantasma implements Serializable{ 

	/**
	 * 
	 */
	private static final long serialVersionUID = -2088904766567638973L;
	/**
	 * 
	 */
	private int guarda1;
	private int guarda2;
	private Integer posX;
	private Integer posY;
	private Boolean movDone = false;
	private Boolean firstTime = false;
	private Boolean die = false;
	private Boolean eat = false;
	public int tiempobolagorda = 0;
	private int imgAR;
	private int imgAB;
	private int imgI;
	private int imgD;
	public boolean returningHome = false;
	public int positionHomeX = 11;
	public int positionHomeY = 13;
	private int[][] destino;
	private Array[][] pAnterior;
	private Array[][] origen;
	private int iteraciones;
	public String op = "";
	private boolean step = true;
	private boolean stepRed = true;
	private boolean stepBlue = true;
	private int contadorPasos=0;
	private boolean arriba =true;
	private boolean abajo= false;
	int vortex = 0;
	ArrayList<Boolean> banderas = new ArrayList<Boolean>();
	ArrayList<String> posNxtMove = new ArrayList<String>();
	ArrayList<Integer[]> listaArraysCone;
	ArrayList<Integer[]> listaArrays;
	ArrayList<Integer[]> listaArrayPosActual;
	FantasmasFirstMove color;
	Integer[] posicionActual;
	private int nuevoMov=1;
	private boolean nuevoActivo=false;
	static ArrayList<ArrayList<Integer>> frutas = new ArrayList<ArrayList<Integer>>();	
	static int rX=0;
	static int rY=0;
	private boolean correcto;
	ArrayList<Integer> path = new ArrayList<Integer>();	
	private boolean siguiendo = true;
	
	public int getGuarda() {
		return guarda1;
	}

	public void setGuarda(int guarda1) {
		this.guarda1 = guarda1;
	}
	
	
	public int getGuarda2() {
		return guarda2;
	}

	public void setGuarda2(int guarda2) {
		this.guarda2 = guarda2;
	}
	
	public int getPositionHomeX() {
		return positionHomeX;
	}

	public void setPositionHomeX(int positionHomeX) {
		this.positionHomeX = positionHomeX;
	}

	public int getPositionHomeY() {
		return positionHomeY;
	}

	public void setPositionHomeY(int positionHomeY) {
		this.positionHomeY = positionHomeY;
	}

	public void banderas() {
		for (int i = 0; i < 30; i++) {
			banderas.add(true);
		}
	}

	public Fantasma(Integer posicionX, Integer posicionY, int AR, int AB, int I, int D,FantasmasFirstMove color) {
		banderas();		
		this.posX = posicionX;
		this.posY = posicionY;
		imgAR = AR;
		imgAB = AB;
		imgI = I;
		imgD = D;
		this.color = color;
		
	}
	

	public Boolean getEat() {
		return eat;
	}

	public void setEat(Boolean eat) {
		this.eat = eat;
	}

	public  Integer getPosX() {
		return posX;
	}

	public void setPosX(Integer posicionX) {
		this.posX = posicionX;
	}

	public  Integer getPosY() {
		return posY;
	}

	public void setPosY(Integer posicionY) {
		this.posY = posicionY;
	}

	public Boolean getMovDone() {
		return this.movDone;
	}

	public void setMovDone(Boolean movDone) {
		this.movDone = movDone;
	}

	public void operaPos(String operacion, String variable) {
		if (operacion.equals("-")) {
			if (variable.equals("x")) {
				this.posX--;
			} else if (variable.equals("y")) {
				this.posY--;
			}
		} else if (operacion.equals("+")) {
			if (variable.equals("x")) {
				this.posX++;
			} else if (variable.equals("y")) {
				this.posY++;
			}
		}
	}

	public Boolean getFirstTime() {

		return this.firstTime;
	}

	public void setFirstTime(Boolean firstTime) {
		this.firstTime = firstTime;
	}

	public boolean getDie() {

		return die;
	}

	public void setDie(Boolean die) {
		this.die = die;
	}

	
	//********************************************************************
	
	public void hazLoTuyo(Catman c) {
			this.know(c);
			if (step) {
				this.firstMoveFantasmas(c);
			}else if ((returningHome==false&&this.color==FantasmasFirstMove.PINK)||
					(returningHome==false&&this.color==FantasmasFirstMove.YELLOW)||
					(returningHome==false&&this.color==FantasmasFirstMove.BLUE)||
					(this.getDie()&&returningHome==false)){
			
				nuevoActivo=false;
				this.move(c);				
			
			}else if (siguiendo==true&&!this.getEat()){
				seguirCatman(c);	
				
			
			}else {
				this.returnHome(c);//mover volviendo
				if (this.posX==positionHomeX&&this.posY==positionHomeY) {
					this.operaPos("+", "x");
					/*MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = imgAB;*/
					nuevoActivo=false;
					returningHome=false;
					this.setEat(false);
					this.setDie(false);					
				}
				
			}
			this.know(c);
	}
	
	//*********************************************************************
	
	
	private void firstMoveFantasmas(Catman c) {
		//comprobar que hayan salido de la casa
		if (this.color==FantasmasFirstMove.BLUE||this.color==FantasmasFirstMove.PINK) {
			
			if (contadorPasos<15) {
				if (nuevoMov==1) {	
					nuevoMov=2;
					move2(c);
					contadorPasos++;
				}else if (nuevoMov==2) {	
					nuevoMov=1;
					move2(c);
					contadorPasos++;
				}
			
			}else if (contadorPasos==15) {
				if(this.color==FantasmasFirstMove.BLUE) {
					nuevoMov=4;
					move2(c);
					contadorPasos++;
				}else {
					nuevoMov=2;
					move2(c);
					contadorPasos++;
				}
			
			}else if(contadorPasos>=16&&contadorPasos<=17){
				if(this.color==FantasmasFirstMove.BLUE) {
					nuevoMov=2;
					move2(c);
					contadorPasos++;
				}else {
					nuevoMov=2;
					move2(c);
					contadorPasos++;
				}
			}else if (contadorPasos==18){
				if(this.color==FantasmasFirstMove.BLUE) {
					nuevoMov=2;
					move2(c);
					contadorPasos++;
				}else {
					nuevoMov=4;
					move2(c);
					contadorPasos++;
				}
				
			}else {
				move(c);
				step=false;
			}
		
		}else if (this.color==FantasmasFirstMove.RED||this.color==FantasmasFirstMove.YELLOW) {
			
			if (contadorPasos<8) {
				if (nuevoMov==1) {	
					move2(c);
					contadorPasos++;
					nuevoMov=2;
				}else if (nuevoMov==2) {	
					move2(c);
					contadorPasos++;
					nuevoMov=1;
				
				}
			}else if (contadorPasos==8) {
				if(this.color==FantasmasFirstMove.RED) {
					nuevoMov=3;
					move2(c);
					contadorPasos++;
				}else {
					nuevoMov=2;
					move2(c);
					contadorPasos++;
				}
			
			}else if(contadorPasos>=9&&contadorPasos<=10){
				if(this.color==FantasmasFirstMove.RED) {
					nuevoMov=2;
					move2(c);
					contadorPasos++;
				}else {
					nuevoMov=2;
					move2(c);
					contadorPasos++;
				}
			}else if (contadorPasos==11){
				if(this.color==FantasmasFirstMove.RED) {
					nuevoMov=2;
					move2(c);
					contadorPasos++;
				}else {
					nuevoMov=4;
					move2(c);
					contadorPasos++;
				}
			}else {
				step=false;
				move(c);
			}
			
		}
	}

	private void random() {
		while (!correcto) {
			rX = (int) (Math.random() * 25) + 1;
			rY = (int) (Math.random() * 25) + 1;
			if (Pacman.mapa[rX][rY]==0) {
				ArrayList<Integer> posiciones = new ArrayList<Integer>();
				posiciones.add(rX);
				posiciones.add(rY);
				frutas.add(posiciones);
				
				correcto=true;
			}
		}
	}

	private void know(Catman c) {
		
		
		if(c.isComebola()) {
			tiempobolagorda=0;
		}
		if (this.getDie()) {
			tiempobolagorda++;
			if (this.getPosX() == c.getpacx() && this.getPosY() == c.getpacy()) {
				eat = true;
				random();
				Pacman.mapa[rX][rY]=35;
				returningHome=true;
			}if (tiempobolagorda > 55) {
				if (!this.getEat()) {
					this.setDie(false);
				}
				tiempobolagorda = 0;
			}
		} else {
			/*returningHome=false;*/
			if (this.getPosX() == c.getpacx() && this.getPosY() == c.getpacy()) {
				c.setFinjuego(true);
				Pacman.fin = true;
			}
		}
	}

	private void move(Catman c) {
		movDone = false;
		int movF;
		while (!movDone) {
			if (nuevoActivo) {
				movF=nuevoMov;
			}else{
				movF = (int) (Math.random() * (4) + 1);
			}
			switch (movF) {
			case 1: // DOWN MOV
				if (Pacman.mapa[this.getPosX() + 1][this.getPosY()] != 1
						&& Pacman.mapa[this.getPosX() + 1][this.getPosY()] != 22
						/*&& !ghostWall.contains(Pacman.mapa[this.getPosX() + 1][this.getPosY()])*/) { // can he move down?

					this.operaPos("+", "x");
					//lastNumber = Pacman.mapa[this.getPosX()][this.getPosY()];
					// DEATH
					if (this.getPosX() == c.getpacx() && this.getPosY() == c.getpacy() && this.getDie()) {
						this.setEat(true);
					
					}if (this.getDie()==true&&this.getEat()==false) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = 28;//panic
						this.setMovDone(true);
					
					}else if (this.getEat()==true) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = 33; //ojos
						this.setMovDone(true);
						

						// ALIVE
					} else if (this.getDie() == false&&this.getEat()==false) {
						
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = imgAB;
						this.setMovDone(true);
					} else {
						this.setMovDone(true);

					}
				}

				break;
			case 2: // UP MOV
				if (Pacman.mapa[this.getPosX() - 1][this.getPosY()] != 1
						/*&& !ghostWall.contains(Pacman.mapa[this.getPosX() - 1][this.getPosY()])*/) {// can he move up?

					this.operaPos("-", "x");
					//lastNumber = Pacman.mapa[this.getPosX()][this.getPosY()];
					// DEATH
					if (this.getPosX() == c.getpacx() && this.getPosY() == c.getpacy() && this.getDie()) {
						this.setEat(true);
					}if (this.getDie()==true&&this.getEat()==false) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = 28;//panic
						this.setMovDone(true);
					}else if (this.getEat()==true) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = 33;//ojos
						this.setMovDone(true);
					
					
					// ALIVE
					} else if (this.getDie() == false&&this.getEat()==false) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = imgAR;
						this.setMovDone(true);
					} else {
						this.setMovDone(true);

					}
				}

				break;
			case 3: // LEFT MOV
				if (Pacman.mapa[this.getPosX()][this.getPosY() - 1] != 1
						&& Pacman.mapa[this.getPosX()][this.getPosY() - 1] != 22
						/*&& !ghostWall.contains(Pacman.mapa[this.getPosX()][this.getPosY() - 1])*/
				/* && returningHome==false */) {// can he move left?

					this.operaPos("-", "y");
					//lastNumber = Pacman.mapa[this.getPosX()][this.getPosY()];
					// DEATH
					// DEATH
					if (this.getPosX() == c.getpacx() && this.getPosY() == c.getpacy() && this.getDie()) {
						this.setEat(true);
					}if (this.getDie()==true&&this.getEat()==false) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = 28;//panic
						this.setMovDone(true);
					}else if (this.getEat()==true) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = 33;//ojos
						this.setMovDone(true);		
						
						// ALIVE
					} else if (this.getDie() == false&&this.getEat()==false) {

						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = imgI;
						this.setMovDone(true);

					} else {
						this.setMovDone(true);
					}
				}

				break;
			case 4: // RIGHT MOV
				if (Pacman.mapa[this.getPosX()][this.getPosY() + 1] != 1
						&& Pacman.mapa[this.getPosX()][this.getPosY() + 1] != 22
						/*&& !ghostWall.contains(Pacman.mapa[this.getPosX()][this.getPosY() + 1])*/
				/* && returningHome==false */) {// can he move right?

					this.operaPos("+", "y");
					//lastNumber = Pacman.mapa[this.getPosX()][this.getPosY()];
					// DEATH
					if (this.getPosX() == c.getpacx() && this.getPosY() == c.getpacy() && this.getDie()) {
						this.setEat(true);
					}if (this.getDie()==true&&this.getEat()==false) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = 28;//panic
						this.setMovDone(true);
					}else if (this.getEat()==true) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = 33;//ojos
						this.setMovDone(true);		
						
						// ALIVE
					} else if (this.getDie() == false&&this.getEat()==false) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = imgD;
						this.setMovDone(true);
					} else {
						this.setMovDone(true);
					}
				}
				break;
			}
		}
	}
	
	
	
	private void move2(Catman c) {
		movDone = false;
		while (!movDone) {
			int movF = nuevoMov;
			switch (movF) {
			case 1: // DOWN MOV
				if (Pacman.mapa[this.getPosX() + 1][this.getPosY()] != 1
						&& Pacman.mapa[this.getPosX() + 1][this.getPosY()] != 22) { 
					this.operaPos("+", "x");
					// DEATH
					if (this.getPosX() == c.getpacx() && this.getPosY() == c.getpacy()) {
						this.setEat(true);
					}if (this.getDie()==true&&this.getEat()==false) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = 28;//panic
						this.setMovDone(true);
					}else if (this.getDie() == true&&this.getEat()==true) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = 33; //ojos
						this.setMovDone(true);															
					// ALIVE
					} else if (this.getDie() == false) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = imgAB;
						this.setMovDone(true);
					} else {
						this.setMovDone(true);
					}
				}

				break;
			case 2: // UP MOV
				if (Pacman.mapa[this.getPosX() - 1][this.getPosY()] != 1) {
					this.operaPos("-", "x");
					// DEATH
					if (this.getPosX() == c.getpacx() && this.getPosY() == c.getpacy()) {
						this.setEat(true);
					}if (this.getDie()==true&&this.getEat()==false) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = 28;//panic
						this.setMovDone(true);
					}else if (this.getDie() == true&&this.getEat()==true) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = 33;//ojos
						this.setMovDone(true);																
					// ALIVE
					} else if (this.getDie() == false) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = imgAR;
						this.setMovDone(true);
					} else {
						this.setMovDone(true);
					}
				}
				break;
			case 3: // LEFT MOV
				if (Pacman.mapa[this.getPosX()][this.getPosY() - 1] != 1
						&& Pacman.mapa[this.getPosX()][this.getPosY() - 1] != 22) {
					this.operaPos("-", "y");
					// DEATH
					if (this.getPosX() == c.getpacx() && this.getPosY() == c.getpacy()) {
						this.setEat(true);
					}if (this.getDie()==true&&this.getEat()==false) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = 28;//panic
						this.setMovDone(true);
					}else if (this.getDie() == true&&this.getEat()==true) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = 33;//ojos
						this.setMovDone(true);															
						// ALIVE
					} else if (this.getDie() == false) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = imgI;
						this.setMovDone(true);
					} else {
						this.setMovDone(true);
					}
				}
				break;
			case 4: // RIGHT MOV
				if (Pacman.mapa[this.getPosX()][this.getPosY() + 1] != 1
						&& Pacman.mapa[this.getPosX()][this.getPosY() + 1] != 22) {
					this.operaPos("+", "y");
					// DEATH
					if (this.getPosX() == c.getpacx() && this.getPosY() == c.getpacy()) {
						this.setEat(true);
					}if (this.getDie()==true&&this.getEat()==false) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = 28;//panic
						this.setMovDone(true);
					}else if (this.getDie() == true&&this.getEat()==true) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = 33;//ojos
						this.setMovDone(true);															
						// ALIVE
					} else if (this.getDie() == false) {
						MapaFantasmas.mapaF[this.getPosX()][this.getPosY()] = imgD;
						this.setMovDone(true);
					} else {
						this.setMovDone(true);
					}
				}
				break;
			}
		}
	}
	
	
	private void seguirCatman(Catman c) {
		int resultY=0;
 		int resultX=0;
		boolean down=false;
		boolean up=false;
		boolean left=false;
		boolean right=false;
		int mov=0;
	
		if (Pacman.mapa[this.getPosX() + 1][this.getPosY()] != 1&&Pacman.mapa[this.getPosX() + 1][this.getPosY()] != 22) { // DOWN MOV
			mov++;
			down=true;
			
			//nuevoMov=1;
		}if (Pacman.mapa[this.getPosX() - 1][this.getPosY()] != 1) {// UP
			mov++;
			up=true;
			
			//nuevoMov=2;
		}
		if (Pacman.mapa[this.getPosX()][this.getPosY() - 1] != 1) {//left
			mov++;
			left=true;
			
			//nuevoMov=3;
		}if (Pacman.mapa[this.getPosX()][this.getPosY() + 1] != 1) {//right
			mov++;
			right=true;
			
			//nuevoMov=4;
		}
		resultX = Math.abs(c.getpacx()-this.posX);
		resultY = Math.abs(c.getpacy()-this.posY);
		boolean buscandoVertice=true;
		//esquinas y pasillos
		if (mov==2) {
			if (nuevoMov==1&&down) {
				buscandoVertice=false;
			}else if (nuevoMov==2&&up) {
				buscandoVertice=false;
			}else if (nuevoMov==3&&left) {
				buscandoVertice=false;
			}else if (nuevoMov==4&&right) {
				buscandoVertice=false;
			}else {				
				buscandoVertice=true;
			}
			//no se cual es el MOV anterior
			if (buscandoVertice) {
				if (left&&right)       {
					if (this.posY > c.getpacy()) {
						nuevoMov=3;
						
					}else {
						nuevoMov=4;
						
					}
				}else if (left&&up)    {
					if (resultX>=resultY) {
						nuevoMov=2;
						
					}else {
						nuevoMov=3;
						
					}
				}else if (left&&down)  {
					if (resultX>=resultY) {
						nuevoMov=1;
						
					}else {
						nuevoMov=3;
						
					}
				}else if (right&&down) {
					if (resultX>=resultY) {
						nuevoMov=1;
					}else {
						nuevoMov=4;
					}						
				}else if (right&&up)   {
					
					if (resultX>=resultY) {
						nuevoMov=2;
						
					}else {
						nuevoMov=4;
						
					}
				}else if (up&&down)    {
					if (this.posX > c.getpacx()) {
						nuevoMov=2;
						
					}else {
						nuevoMov=1;
					}
				}
				
				move(Pacman.player1);
				
			}else {
				move(Pacman.player1);
			}
		}else if (mov==3) {
			if(resultY>=resultX) { //horizontal					
				if (this.posY > c.getpacy()) {
					if (left) {
						nuevoMov=3;
					}else {
						if (up) {
							nuevoMov=2;
						}else if (down) {
							nuevoMov=1;
						}else if (right) {
							nuevoMov=4;
						}
					}
				}else {
					if (right) {
						nuevoMov=4;
					}else if (up) {
						nuevoMov=2;
					}else if (down) {
						nuevoMov=1;
					}else if (left) {
						nuevoMov=1;
					}
				}
			}else {  //vertical
				if (this.posX > c.getpacx()) {
					if (up) {
						nuevoMov=2;
					}else if (left) {
						nuevoMov=3;
					}else if (right) {
						nuevoMov=4;
					}else if (down) {
						nuevoMov=1;
					}						
				}else {
					if (down) {
						nuevoMov=1;
					}else if (left) {
						nuevoMov=3;
					}else if (right) {
						nuevoMov=4;
					}else if (up) {
						nuevoMov=2;
					}						
				}
			}
			nuevoActivo=true;					
			move(Pacman.player1);
		}else if (mov==4) {
			if(resultY>=resultX) {
				if (c.getpacy()>this.posY) {
					nuevoMov=4;
				}else {
					nuevoMov=3;
				}
			}else {
				if (c.getpacx()>this.posX) {
					nuevoMov=1;
				}else {
					nuevoMov=2;
				}
				
			}
			nuevoActivo=true;					
			move(Pacman.player1);
		}	
	}
	
	private void returnHome(Catman c) {
		int resultY=0;
 		int resultX=0;
		boolean down=false;
		boolean up=false;
		boolean left=false;
		boolean right=false;
		int mov=0;
	
		if (Pacman.mapa[this.getPosX() + 1][this.getPosY()] != 1&&Pacman.mapa[this.getPosX() + 1][this.getPosY()] != 22) { // DOWN MOV
			mov++;
			down=true;
			
			//nuevoMov=1;
		}if (Pacman.mapa[this.getPosX() - 1][this.getPosY()] != 1) {// UP
			mov++;
			up=true;
			
			//nuevoMov=2;
		}
		if (Pacman.mapa[this.getPosX()][this.getPosY() - 1] != 1) {//left
			mov++;
			left=true;
			
			//nuevoMov=3;
		}if (Pacman.mapa[this.getPosX()][this.getPosY() + 1] != 1) {//right
			mov++;
			right=true;
			
			//nuevoMov=4;
		}
		resultX = Math.abs(positionHomeX-this.posX);
		resultY = Math.abs(positionHomeY-this.posY);
		boolean buscandoVertice=true;
		//esquinas y pasillos
		if (mov==2) {
			if (nuevoMov==1&&down) {
				buscandoVertice=false;
			}else if (nuevoMov==2&&up) {
				buscandoVertice=false;
			}else if (nuevoMov==3&&left) {
				buscandoVertice=false;
			}else if (nuevoMov==4&&right) {
				buscandoVertice=false;
			}else {				
				buscandoVertice=true;
			}
			//no se cual es el MOV anterior
			if (buscandoVertice) {
				if (left&&right)       {
					if (this.posY > positionHomeY) {
						nuevoMov=3;
						
					}else {
						nuevoMov=4;
						
					}
				}else if (left&&up)    {
					if (resultX>=resultY) {
						nuevoMov=2;
						
					}else {
						nuevoMov=3;
						
					}
				}else if (left&&down)  {
					if (resultX>=resultY) {
						nuevoMov=1;
						
					}else {
						nuevoMov=3;
						
					}
				}else if (right&&down) {
					if (resultX>=resultY) {
						nuevoMov=1;
						
					}else {
						nuevoMov=4;
						
					}						
				}else if (right&&up)   {
					
					if (resultX>=resultY) {
						nuevoMov=2;
						
					}else {
						nuevoMov=4;
						
					}
				}else if (up&&down)    {
					if (this.posX > positionHomeX) {
						nuevoMov=2;
						
					}else {
						nuevoMov=1;
					}
				}
				
				move(Pacman.player1);
				
			}else {
				move(Pacman.player1);
			}
		}else if (mov==3) {
			if(resultY>=resultX) {					
				if (this.posY > positionHomeY) {
					if (left) {
						nuevoMov=3;
					}else {
						if (up) {
							nuevoMov=2;
						}else if (right) {
							nuevoMov=4;
						}else if (down) {
							nuevoMov=1;
						}
					}
				}else {
					if (right) {
						nuevoMov=4;
					}else if (up) {
						nuevoMov=2;
					}else if (down) {
						nuevoMov=1;
					}else if (left) {
						nuevoMov=1;
					}
				}
			}else {
				if (this.posX > positionHomeX) {
					if (up) {
						nuevoMov=2;
					}else if (left) {
						nuevoMov=3;
					}else if (right) {
						nuevoMov=4;
					}else if (down) {
						nuevoMov=1;
					}						
				}else {
					if (down) {
						nuevoMov=1;
					}else if (left) {
						nuevoMov=3;
					}else if (right) {
						nuevoMov=4;
					}else if (up) {
						nuevoMov=2;
					}						
				}
			}
			nuevoActivo=true;					
			move(Pacman.player1);
		}else if (mov==4) {
			if(resultY>=resultX) {
				if (positionHomeY>this.posY) {
					nuevoMov=4;
				}else {
					nuevoMov=3;
				}
			}else {
				if (positionHomeX>this.posX) {
					nuevoMov=1;
				}else {
					nuevoMov=2;
				}
				
			}
			nuevoActivo=true;					
			move(Pacman.player1);
		}
	
	}


	
	
	
	
	public boolean initListas () {
		listaArraysCone = new ArrayList<Integer[]>();
		listaArrays = new ArrayList<Integer[]>();
		//listaArrayPosActual = new ArrayList<Integer[]>();
	
		
		
		
		//vectores conexiones
		
		
		listaArraysCone.add(new Integer[]{1,6});//index: 0 
		listaArraysCone.add(new Integer[]{0,2,7});//1
		listaArraysCone.add(new Integer[]{1,9});//2
		listaArraysCone.add(new Integer[]{4,10});//3
		listaArraysCone.add(new Integer[]{3,5,12});//4
		listaArraysCone.add(new Integer[]{4,13});//5
      
		listaArraysCone.add(new Integer[]{0,7,14});//6
		listaArraysCone.add(new Integer[]{1,6,8,15});//7
		listaArraysCone.add(new Integer[]{7,9,16});//8
		listaArraysCone.add(new Integer[]{2,8,10});//9
		listaArraysCone.add(new Integer[]{3,9,11});//10
		listaArraysCone.add(new Integer[]{10,12,19});//11
		listaArraysCone.add(new Integer[]{4,11,13,20});//12
		listaArraysCone.add(new Integer[]{5,12,21});//13
        
		listaArraysCone.add(new Integer[]{6,15});//14
		listaArraysCone.add(new Integer[]{7,14,26});//15
		listaArraysCone.add(new Integer[]{8,17});//16
		listaArraysCone.add(new Integer[]{16,23});//17
		listaArraysCone.add(new Integer[]{19,24});//18
		listaArraysCone.add(new Integer[]{11,18});//19
		listaArraysCone.add(new Integer[]{12,21,29});//20
		listaArraysCone.add(new Integer[]{13,20});//21

		listaArraysCone.add(new Integer[]{23,27});//22
		listaArraysCone.add(new Integer[]{17,22,24});//23
		listaArraysCone.add(new Integer[]{18,23,25});//24
		listaArraysCone.add(new Integer[]{24,28});//25
        
      
		listaArraysCone.add(new Integer[]{15,27,33});//26
		listaArraysCone.add(new Integer[]{22,26,30});//27
		listaArraysCone.add(new Integer[]{25,29,31});//28
		listaArraysCone.add(new Integer[]{20,38});//29
        
		listaArraysCone.add(new Integer[]{27,31,34});//30
		listaArraysCone.add(new Integer[]{28,30,37});//31
        
		listaArraysCone.add(new Integer[]{33,40});//32
		listaArraysCone.add(new Integer[]{26,32,34,42});//33
		listaArraysCone.add(new Integer[]{30,33,35});//34
		listaArraysCone.add(new Integer[]{34,44});//35
		listaArraysCone.add(new Integer[]{37,45});//36
		listaArraysCone.add(new Integer[]{31,36,38});//37
		listaArraysCone.add(new Integer[]{29,37,39,47});//38
		listaArraysCone.add(new Integer[]{38,49});//39
        
        
		listaArraysCone.add(new Integer[]{32,41});//40
		listaArraysCone.add(new Integer[]{40,51});//41
		listaArraysCone.add(new Integer[]{33,43,52});//42
		listaArraysCone.add(new Integer[]{42,44});//43
		listaArraysCone.add(new Integer[]{35,43,45});//44
		listaArraysCone.add(new Integer[]{36,44,46});//45
		listaArraysCone.add(new Integer[]{45,47,56});//46
		listaArraysCone.add(new Integer[]{38,46,57});//47
		listaArraysCone.add(new Integer[]{49,58});//48
		listaArraysCone.add(new Integer[]{39,48});//49
        
        
		listaArraysCone.add(new Integer[]{51,60});//50
		listaArraysCone.add(new Integer[]{41,50,52});//51
		listaArraysCone.add(new Integer[]{42,51});//52
		listaArraysCone.add(new Integer[]{43,54});//53
		
		listaArraysCone.add(new Integer[]{53,61});//54
		listaArraysCone.add(new Integer[]{56,62});//55
		listaArraysCone.add(new Integer[]{46,55});//56
		listaArraysCone.add(new Integer[]{47,58});//57
		listaArraysCone.add(new Integer[]{48,57,59});//58
		listaArraysCone.add(new Integer[]{58,63});//59
		
		listaArraysCone.add(new Integer[]{50,61});//60
		listaArraysCone.add(new Integer[]{54,60,62});//61
		listaArraysCone.add(new Integer[]{55,61,63});//62
		listaArraysCone.add(new Integer[]{59,62});//63
		
		
		
		
		
        //vectores posiciones
		
        listaArrays.add(new Integer[]{1,1});//index: 0
        listaArrays.add(new Integer[]{1,6});
        listaArrays.add(new Integer[]{1,12});
        listaArrays.add(new Integer[]{1,15});
        listaArrays.add(new Integer[]{1,21});
        listaArrays.add(new Integer[]{1,26});
      
        listaArrays.add(new Integer[]{5,1});
        listaArrays.add(new Integer[]{5,6});
        listaArrays.add(new Integer[]{5,9});
        listaArrays.add(new Integer[]{5,12});
        listaArrays.add(new Integer[]{5,15});
        listaArrays.add(new Integer[]{5,18});
        listaArrays.add(new Integer[]{5,21});
        listaArrays.add(new Integer[]{5,26});
        
        listaArrays.add(new Integer[]{8,1});
        listaArrays.add(new Integer[]{8,6});
        listaArrays.add(new Integer[]{8,9});
        listaArrays.add(new Integer[]{8,12});
        listaArrays.add(new Integer[]{8,15});
        listaArrays.add(new Integer[]{8,18});
        listaArrays.add(new Integer[]{8,21});
        listaArrays.add(new Integer[]{8,26});

        listaArrays.add(new Integer[]{11,9});
        listaArrays.add(new Integer[]{11,12});
        listaArrays.add(new Integer[]{11,15});
        listaArrays.add(new Integer[]{11,18});
        
      
        listaArrays.add(new Integer[]{14,6});
        listaArrays.add(new Integer[]{14,9});
        listaArrays.add(new Integer[]{14,18});
        listaArrays.add(new Integer[]{14,21});
        
        listaArrays.add(new Integer[]{17,9});
        listaArrays.add(new Integer[]{17,18});
        
        listaArrays.add(new Integer[]{20,1});
        listaArrays.add(new Integer[]{20,6});
        listaArrays.add(new Integer[]{20,9});
        listaArrays.add(new Integer[]{20,12});
        listaArrays.add(new Integer[]{20,15});
        listaArrays.add(new Integer[]{20,18});
        listaArrays.add(new Integer[]{20,21});
        listaArrays.add(new Integer[]{20,26});
        
        
        listaArrays.add(new Integer[]{23,1});
        listaArrays.add(new Integer[]{23,3});
        listaArrays.add(new Integer[]{23,6});
        listaArrays.add(new Integer[]{23,9});
        listaArrays.add(new Integer[]{23,12});
        listaArrays.add(new Integer[]{23,15});
        listaArrays.add(new Integer[]{23,18});
        listaArrays.add(new Integer[]{23,21});
        listaArrays.add(new Integer[]{23,24});
        listaArrays.add(new Integer[]{23,26});
        
        listaArrays.add(new Integer[]{26,1});
        listaArrays.add(new Integer[]{26,3});
        listaArrays.add(new Integer[]{26,6});
        listaArrays.add(new Integer[]{26,9});
        listaArrays.add(new Integer[]{26,12});
        listaArrays.add(new Integer[]{26,15});
        listaArrays.add(new Integer[]{26,18});
        listaArrays.add(new Integer[]{26,21});
        listaArrays.add(new Integer[]{26,24});
        listaArrays.add(new Integer[]{26,26});
        
        
        listaArrays.add(new Integer[]{29,1});
        listaArrays.add(new Integer[]{29,12});
        listaArrays.add(new Integer[]{29,15});
        listaArrays.add(new Integer[]{29,26});//index: 63
		
        return true;
 
        
	}
	
	
	
}