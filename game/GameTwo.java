package game;

import gameEngine.*;
import graphicslib3D.*;

import java.awt.Color;
import java.awt.DisplayMode;
import java.io.*;
import java.net.InetAddress;
import java.net.InetAddress.*;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.*;
import sage.model.loader.ogreXML.*;
import sage.scene.Model3DTriMesh;
import sage.app.BaseGame;
import sage.model.loader.OBJLoader;
import sage.networking.IGameConnection.ProtocolType;
import sage.physics.*;
import sage.renderer.IRenderer;
import sage.scene.*;
import sage.scene.shape.*;
import sage.scene.state.TextureState;
import sage.terrain.TerrainBlock;
import sage.texture.Texture;
import sage.texture.Texture.ApplyMode;
import sage.texture.TextureManager;
import sage.camera.ICamera;
import sage.camera.JOGLCamera;
import sage.display.*;
import sage.event.*;
import sage.input.IInputManager;
import net.java.games.input.Controller;



//Scripting
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;



//terrain
import sage.terrain.*;
import sage.scene.state.*;
//physics
import sage.physics.*;
import net.java.games.input.Event;
import sage.input.*;
import sage.input.action.AbstractInputAction;

//Sound
import sage.audio.*;

import com.jogamp.openal.ALFactory;

public class GameTwo extends BaseGame {

	private float time = 0;
	private HUDString scorePlayer1;
	private HUDString timeString;
	public IDisplaySystem display;
	// Create Cameras
	private ICamera camera1;
	private IRenderer renderer;
	private IInputManager inManager;
	private Camera3PointController cam1Controller;
	private TriMesh player;
	private Speed speed = new Speed();
	private float finalSpeed;
	private int player1Controller;
	private Group scene;
	private Group diamondGroup;
	private Group doomGroup;
	private SkyBox skybox;
	private Scanner in = new Scanner(System.in);
	private boolean connectedToServer = false;
	private boolean serverCreated = false;
	private Client thisClient;
	private String serverAddress;
	//server
	private TCPGameServer mySever;
	
	//scripting
	private String scriptFileName;
	private ScriptEngine jsEngine;
	private SceneNode sceneScript;
	
	//terrain
	private TerrainBlock aqua;
	private TerrainBlock terra;
   
	//physics
	private TriMesh ball;
	private OBJLoader loader2 = new OBJLoader();
	private IPhysicsEngine physicsEngine;
	private IPhysicsObject ballP;
	//private IPhysicsObject groundPlaneP;
	private boolean running;
	
	//Sound
	private IAudioManager audioMgr;
	private Sound oceanSound;
   
   private TextureState narwhalTS;
   private Model3DTriMesh nar;
   private  Group model;
	
	protected void initGame(){

		finalSpeed = speed.getSpeed();
		inManager = getInputManager();
		
		setControllers();
		createClient();
		createDisplaySystem();
		renderer = display.getRenderer();
		
		createScene();

		initInput();
		
	//physics
		initPhysicsSystem();
		running = false;
		
	 //Sound
      initAudio();
	}
	
	public void update(float elapsedTimeMS){
	//	scorePlayer1.setText("Score = " + player.getScore());
	//	time += elapsedTimeMS;
	//	DecimalFormat df = new DecimalFormat("0.0");
	//	timeString.setText("Time = "+ df.format(time/1000));
	/*	for(SceneNode s: diamondGroup){
			if(s instanceof CreateDimond){
				CreateDimond p = (CreateDimond) s;
				if(p.getWorldBound().intersects(player.getWorldBound()) && p.getIsHit() == false && player != null){
					new DimondCrash(p,player);
					tBox.reScale();	
				}
			}
		}
		//player.setInit(true);
	*/
		
		Point3D camLoc = camera1.getLocation();
		Matrix3D camTranslation = new Matrix3D();
		camTranslation.translate(camLoc.getX(), camLoc.getY(), camLoc.getZ());
		skybox.setLocalTranslation(camTranslation);
		cam1Controller.update(elapsedTimeMS);
		
		camTranslation = new Matrix3D();
		camTranslation.translate(camLoc.getX(), camLoc.getY(), camLoc.getZ());
		skybox.setLocalTranslation(camTranslation);
		//move1.update(elapsedTimeMS);
		if (thisClient != null) {
			thisClient.processPackets();
		}
		//physics
       if (running){ 
            Matrix3D mat;
            Vector3D translateVec;
            physicsEngine.update(20.0f);
            Iterator<SceneNode> iterate = scene.iterator();
           while(iterate.hasNext()){
        	   SceneNode s = (SceneNode) iterate.next();
               if (s.getPhysicsObject() != null){ 
                  mat = new Matrix3D(s.getPhysicsObject().getTransform());
                  translateVec = mat.getCol(3);
                  s.getLocalTranslation().setCol(3,translateVec);
               }
            }
        }
		nar.updateAnimation(elapsedTimeMS);
		super.update(elapsedTimeMS);
		
	}
   
   public IDisplaySystem getDisplay()
   {
      return display;
   }
   
	private void createPlayers(){
		//Set up player 1
		OBJLoader loader = new OBJLoader();
		player = loader.loadModel("./objects/boat3.obj");
	    //UV texture but you must map that png to the UV in maya to do so create a material then for the color load that png that you exported and edited as a UV map
	    TextureState boatState;
	    Texture boatTexture = TextureManager.loadTexture2D("./materials/boato.png");
	    boatTexture.setApplyMode(sage.texture.Texture.ApplyMode.Replace);
	    boatState = (TextureState)
	    display.getRenderer().createRenderState(RenderState.RenderStateType.Texture);
	    boatState.setTexture(boatTexture,0);
	    boatState.setEnabled(true);
	      
	    // apply the texture to the terrain
	    player.setRenderState(boatState);
	       //--------------
	    // player.setTexture(boatTexture,0);
	    player.rotate(270, new Vector3D(0.0f,1.0f,0.0f));
	    player.scale(.05f, .05f, .05f);
	    Random rand = new Random();
	    player.translate(rand.nextInt(50)+3, 0, 1);
		player.updateLocalBound();
		
		//player 1 camera creatation
		camera1 = new JOGLCamera(renderer); 
		camera1.setPerspectiveFrustum(45, 1, 0.01, 1000);
		camera1.setViewport(0.0, 1.0, 0.0, 1.0);
		cam1Controller = new Camera3PointController(camera1, player);
		BoatPropel propel = new BoatPropel(player, this, cam1Controller, aqua, terra, speed);
		player.addController(propel);
		scene.addChild(player);
		
		createPlayerHUDs();
	}
	private void createPlayerHUDs(){
		//create player1 hud
		HUDString player1ID = new HUDString("Player1");
		player1ID.setName("Player1ID");
		player1ID.setLocation(0.0,0.06);
		player1ID.setRenderMode(sage.scene.SceneNode.RENDER_MODE.ORTHO);
		player1ID.setColor(Color.red);
		player1ID.setCullMode(sage.scene.SceneNode.CULL_MODE.NEVER);
		camera1.addToHUD(player1ID);
		
		//scorePlayer1 = new HUDString("Score = " + player.getScore());
		//scorePlayer1.setName("ScorePlayer1");
		//scorePlayer1.setLocation(0.0,0.01);
		//scorePlayer1.setRenderMode(sage.scene.SceneNode.RENDER_MODE.ORTHO);
		//scorePlayer1.setColor(Color.red);
		//scorePlayer1.setCullMode(sage.scene.SceneNode.CULL_MODE.NEVER);
		//camera1.addToHUD(scorePlayer1);
				
	}
	private void createScene(){
		
		scene = new Group("Root Node");
		// creates axisscene = new Group("Root Node");
		// construct a skybox for the scene
		skybox = new SkyBox("SkyBox", 20.0f, 20.0f, 20.0f);
		// load skybox textures
		Texture northTex = TextureManager.loadTexture2D("./images/Front.png", Texture.MinificationFilter.BilinearNoMipMaps,Texture.MagnificationFilter.Bilinear);
		Texture southTex = TextureManager.loadTexture2D("./images/Back.png", Texture.MinificationFilter.BilinearNoMipMaps,Texture.MagnificationFilter.Bilinear);
		Texture eastTex = TextureManager.loadTexture2D("./images/Right.png", Texture.MinificationFilter.BilinearNoMipMaps,Texture.MagnificationFilter.Bilinear);
		Texture westTex = TextureManager.loadTexture2D("./images/Left.png", Texture.MinificationFilter.BilinearNoMipMaps,Texture.MagnificationFilter.Bilinear);
		Texture upTex = TextureManager.loadTexture2D("./images/Up.png", Texture.MinificationFilter.BilinearNoMipMaps,Texture.MagnificationFilter.Bilinear);
		Texture downTex = TextureManager.loadTexture2D("./images/Down.png", Texture.MinificationFilter.BilinearNoMipMaps,Texture.MagnificationFilter.Bilinear);
		
		upTex.setApplyMode(ApplyMode.Replace);
		// attach textures to skybox
		skybox.setTexture(SkyBox.Face.North,northTex);
		skybox.setTexture(SkyBox.Face.South,southTex);
		skybox.setTexture(SkyBox.Face.East,eastTex);
		skybox.setTexture(SkyBox.Face.West,westTex);
		skybox.setTexture(SkyBox.Face.Up,upTex);
		skybox.setTexture(SkyBox.Face.Down,downTex);
		// ...etc...
		scene.addChild(skybox);
		
		ScriptEngineManager factory = new ScriptEngineManager();
	    scriptFileName = "./scripts/CreateWorld.js";
	    // get a list of the script engines on this platform
	    List<ScriptEngineFactory> list = factory.getEngineFactories();
	    System.out.println("Script Engine Factories found:");
	    for (ScriptEngineFactory f : list){ 
	        System.out.println(" Name = " + f.getEngineName()+ " language = " + f.getLanguageName() + " extensions = " + f.getExtensions());
	    }
	    // get the JavaScript engine
	    jsEngine = factory.getEngineByName("js");
		this.runScript();
	    sceneScript = (SceneNode) jsEngine.get("rootNode");
	  
		//Create a group of diamonds 
		diamondGroup = new Group("root");
		
		
		// randomly create 50 dimonds 
		
		
		//Terrain
	    aqua = initTerrain("./images/waves4.jpg", "./images/Down.png", 0.005f);
	    terra = initTerrain("./images/island.jpg", "./images/texisland.jpg", 0.005f);
		
	    //physics
	    //add a graphical Ball    
	    /*
	    OBJLoader loader2 = new OBJLoader();
	    ball = loader2.loadModel("./src/objects/cannon.obj");
	    //UV texture but you must map that png to the UV in maya to do so create a material then for the color load that png that you exported and edited as a UV map
	    TextureState cannonState;
	    Texture cannonTexture = TextureManager.loadTexture2D("./materials/cannon_tex.png");
	    cannonTexture.setApplyMode(sage.texture.Texture.ApplyMode.Replace);
	    cannonState = (TextureState)
	    display.getRenderer().createRenderState(RenderState.RenderStateType.Texture);
	    cannonState.setTexture(cannonTexture,0);
	    cannonState.setEnabled(true);
	    
	    // apply the texture to the terrain
	    
       ball.setRenderState(cannonState);
       ball.translate(1, 1, 1);
       ball.scale(0.05f, 0.05f, 0.05f);
       scene.addChild(ball);
       ball.updateGeometricState(1.0f, true);
		*/
	   createPlayers();

      		OgreXMLParser loader = new OgreXMLParser();
		try
      {
			model = loader.loadModel("./animation/narwhal.mesh.xml", "./animation/narwhal.mtl", "./animation/narwhal.skeleton.xml");
		   model.updateGeometricState(0, true);
			java.util.Iterator<SceneNode> modelIterator = model.iterator();
			nar = (Model3DTriMesh) modelIterator.next();
		}
      catch (Exception e)
      {
			e.printStackTrace();
			System.out.println("Model did not load");
		}
		//this.updateLocalBound();
		//this.updateWorldBound();
		
		//npc = new Sphere();
		nar.updateLocalBound();
		nar.updateWorldBound();
		nar.translate(2, 0, 2);
       nar.rotate(180, new Vector3D(0.0f,1.0f,0.0f));
	    nar.scale(.05f, .05f, .05f);
		addGameWorldObject(nar);
      
      Texture narTexture = TextureManager.loadTexture2D("./materials/norwals.png");
      narTexture.setApplyMode(sage.texture.Texture.ApplyMode.Replace);
      narwhalTS = (TextureState) getDisplay().getRenderer().createRenderState(RenderState.RenderStateType.Texture);
      narwhalTS.setTexture(narTexture, 0);
      narwhalTS.setEnabled(true);
      nar.setRenderState(narwhalTS);
      nar.updateRenderStates();
      nar.startAnimation("my_animation");

	   this.addGameWorldObject(scene);
	   this.addGameWorldObject(sceneScript);
	}
	private void initInput(){
		
		String keyBoard = inManager.getKeyboardName();
		String player1gamePad = inManager.getControllers().get(player1Controller).getName();
		
		// Initialize motion controls for Player 1
		ActionQuit quitGame = new ActionQuit(this);
		ActionMvForwrd moveForwardBack = new ActionMvForwrd(player,speed,cam1Controller,this);
		ActionLeftRight moveLeftRight = new ActionLeftRight(player, finalSpeed,cam1Controller, this);
		OrbitAroundAction orbit = new OrbitAroundAction(cam1Controller);
		ZoomAction zoom = new ZoomAction(cam1Controller);
		ActionYaw yaw = new ActionYaw(cam1Controller);
		ActionPitch pitchFalse1 = new ActionPitch(cam1Controller);
		OrbitPitch pitchTrue1 = new OrbitPitch(cam1Controller);
		StartAction startAction = new StartAction();
		//move1 = new Move(player1, finalSpeed, cam1Controller, inManager, player1gamePad);
		
		
		
		// associated gamePad with actions on the screen 
		inManager.associateAction(player1gamePad, net.java.games.input.Component.Identifier.Axis.Y, moveForwardBack,IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inManager.associateAction(player1gamePad, net.java.games.input.Component.Identifier.Axis.X, moveLeftRight,IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inManager.associateAction(player1gamePad, net.java.games.input.Component.Identifier.Axis.Z, orbit,IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inManager.associateAction(player1gamePad, net.java.games.input.Component.Identifier.Button._7, quitGame,IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
		inManager.associateAction(player1gamePad, net.java.games.input.Component.Identifier.Axis.RX, yaw,IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inManager.associateAction(player1gamePad, net.java.games.input.Component.Identifier.Axis.RY, pitchFalse1,IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inManager.associateAction(player1gamePad, net.java.games.input.Component.Identifier.Button._0, zoom,IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inManager.associateAction(player1gamePad, net.java.games.input.Component.Identifier.Button._1, zoom,IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inManager.associateAction(player1gamePad, net.java.games.input.Component.Identifier.Button._4, pitchTrue1,IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inManager.associateAction(player1gamePad, net.java.games.input.Component.Identifier.Button._5, pitchTrue1,IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		
		//associated keyboard with action on the screen
		inManager.associateAction(keyBoard, net.java.games.input.Component.Identifier.Key.ESCAPE, quitGame,IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
		
		inManager.associateAction(keyBoard, net.java.games.input.Component.Identifier.Key.A, moveLeftRight, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inManager.associateAction(keyBoard, net.java.games.input.Component.Identifier.Key.D, moveLeftRight, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inManager.associateAction(keyBoard, net.java.games.input.Component.Identifier.Key.W, moveForwardBack, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inManager.associateAction(keyBoard, net.java.games.input.Component.Identifier.Key.S, moveForwardBack, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
		inManager.associateAction(keyBoard, net.java.games.input.Component.Identifier.Key.Q, zoom, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inManager.associateAction(keyBoard, net.java.games.input.Component.Identifier.Key.E, zoom, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inManager.associateAction(keyBoard, net.java.games.input.Component.Identifier.Key.LEFT, yaw, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inManager.associateAction(keyBoard, net.java.games.input.Component.Identifier.Key.RIGHT, yaw, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inManager.associateAction(keyBoard, net.java.games.input.Component.Identifier.Key.J, orbit, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inManager.associateAction(keyBoard, net.java.games.input.Component.Identifier.Key.L, orbit, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inManager.associateAction(keyBoard, net.java.games.input.Component.Identifier.Key.UP, pitchFalse1, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inManager.associateAction(keyBoard, net.java.games.input.Component.Identifier.Key.DOWN, pitchFalse1, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inManager.associateAction(keyBoard, net.java.games.input.Component.Identifier.Key.I, pitchTrue1, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		inManager.associateAction(keyBoard, net.java.games.input.Component.Identifier.Key.K, pitchTrue1, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        inManager.associateAction(keyBoard, net.java.games.input.Component.Identifier.Key.SPACE, startAction, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
  }
	protected void render(){
		if(camera1 != null){
			//render camera1
			renderer.setCamera(camera1);
			super.render();
		}
	}
	public void addGameWorldObject(SceneNode s){
		super.addGameWorldObject(s);
	}
	public void addToSkyBoxScene(SceneNode s){
		scene.addChild(s);
	}
	private void setControllers(){
		int i = 0;
		for(Controller controllers : inManager.getControllers()){
			System.out.println("Contoller #" + i + " " + controllers.getName());
			i=i+1;
		}
		System.out.println("Please select controller # for player 1.");
		player1Controller =  in.nextInt();
	}
	
	private IDisplaySystem createDisplaySystem(){
		
		display = new MyDisplaySystem("sage.renderer.jogl.JOGLRenderer");
		System.out.println("Waiting for display creation...");
		int count = 0;
		// wait until display creation completes or a timeout occurs
		while (!display.isCreated()){
			try{ Thread.sleep(10); }
			catch (InterruptedException e){
				throw new RuntimeException("Display creation interrupted");
			}
			count++;
			System.out.print("-");
			if (count % 80 == 0) {
				System.out.println(); 
			}
			if (count > 2000){
				throw new RuntimeException("Unable to create display");
			}
		}
		System.out.println();
		return display ;
	}

	public void setIsConnectedToServer(boolean b) {
		connectedToServer = b;
	}
	public boolean getIsConnectedToServer(){
		return connectedToServer;
	}
	public Vector3D getPlayerPosition() {
		
		return player.getLocalTranslation().getCol(3);
	}
	
	private void createClient(){
		
		boolean validInput = false;
		while(!validInput ){
			System.out.println("Would you like to connect to a game server? If you would like to connect to a server type \"yes\" else type \"no\". ");
			String server = in.next();
			if(server.equals("yes")){
				validInput = true;
				try{
					System.out.println("Please enter in the sever IP address");
					serverAddress = in.next();
					thisClient = new Client(InetAddress.getByName(serverAddress), this); 
					 
				}catch (UnknownHostException e) { 
					e.printStackTrace();
				}catch (IOException e) {
					e.printStackTrace(); 
				}
				if (thisClient != null) {
					 System.out.println("Sending Join Message");
					 thisClient.sendJoinMessage(); 
					 thisClient.askForNPCinfo();
				}

			}else if(server.equals("no")){
				validInput = true;
			}
		}
	}
	public Client getThisClient(){
		return thisClient;
	}
	private void runScript(){
		try{ 
			FileReader fileReader = new FileReader(scriptFileName);
			jsEngine.eval(fileReader); //execute the script statements in the file
			fileReader.close();
		}catch (FileNotFoundException e1){ 
			System.out.println(scriptFileName + " not found " + e1); 
	    }catch (IOException e2){ 
	        System.out.println("IO problem with " + scriptFileName + e2); 
	    }catch (ScriptException e3){ 
	       System.out.println("ScriptException in " + scriptFileName + e3); 
	    }catch (NullPointerException e4){ 
	    	System.out.println ("Null ptr exception in " + scriptFileName + e4); 
	    }
	}
	public void serverAlive(boolean b){
		serverCreated = b;
	}
	public boolean isServerAlive(){
		return serverCreated;
	}
	public void myShutdown(){
		super.shutdown();
		if (thisClient != null) {
			 System.out.println("Sending Bye Message");
			 thisClient.sendByeMessage(); 
			 try{
				thisClient.shutdown();
			}catch (IOException e){
			
				e.printStackTrace();
			}
		}
		this.setGameOver(true);
	}
	//Terrain
   private TerrainBlock initTerrain(String height, String tex, float depth){
	   // create height map and terrain block
       ImageBasedHeightMap myHeightMap = new ImageBasedHeightMap(height);
       TerrainBlock imageTerrain = createTerBlock(myHeightMap, depth);
       // create texture and texture state to color the terrain
       TextureState grassState;
       Texture grassTexture = TextureManager.loadTexture2D(tex);
       grassTexture.setApplyMode(sage.texture.Texture.ApplyMode.Replace);
       grassState = (TextureState)
      display.getRenderer().createRenderState(RenderState.RenderStateType.Texture);
       grassState.setTexture(grassTexture,0);
       grassState.setEnabled(true);
       // apply the texture to the terrain
       imageTerrain.setRenderState(grassState);
       scene.addChild(imageTerrain);
       return imageTerrain;
    }
	    
    //Terrain
     private TerrainBlock createTerBlock(AbstractHeightMap heightMap, float depth){
    	 
       float heightScale = depth; // .005f;
       Vector3D terrainScale = new Vector3D(.2, heightScale, .2);
       // use the size of the height map as the size of the terrain
       int terrainSize = heightMap.getSize();
       // specify terrain origin so heightmap (0,0) is at world origin
       float cornerHeight = heightMap.getTrueHeightAtPoint(0, 0) * heightScale;
       Point3D terrainOrigin = new Point3D(0, -cornerHeight, 0);
       // create a terrain block using the height map
       String name = "Terrain:" + heightMap.getClass().getSimpleName();
       TerrainBlock tb = new TerrainBlock(name, terrainSize, terrainScale,
       heightMap.getHeightData(), terrainOrigin);
       return tb;
    }
    
    //physics
    protected void initPhysicsSystem(){
    	
       String engine = "sage.physics.JBullet.JBulletPhysicsEngine";
       physicsEngine = PhysicsEngineFactory.createPhysicsEngine(engine);
       physicsEngine.initSystem();
       float[] gravity = {0, -1f, 0};
       physicsEngine.setGravity(gravity);
    }
    
    //physics
     private void createSagePhysicsWorld(){ 
       // add the ball physics
    	 // add the ball physics
         float mass = 1.0f;
         float lin [] = {6.0f, 1.0f, 1.0f};
         ballP = physicsEngine.addSphereObject(physicsEngine.nextUID(), mass, ball.getWorldTransform().getValues(),1.0f);
         ballP.setLinearVelocity(lin);
         ballP.setAngularVelocity(lin);
         ball.setPhysicsObject(ballP);
    }
     
     //sound
   	 private void initAudio(){
   		 audioMgr = AudioManagerFactory.createAudioManager("sage.audio.joal.JOALAudioManager");
   		
   		 if(!audioMgr.initialize()){
   			System.out.println("");
   		    System.out.println("Audio Manager failed to initialize!");
   		   // return;
   		  }
   		  AudioResource resource1 = audioMgr.createAudioResource("./sounds/ocean2.wav", AudioResourceType.AUDIO_STREAM);
   		  
   		  oceanSound = new Sound(resource1, SoundType.SOUND_EFFECT, 90, false);
   		  
   		  
   		  oceanSound.initialize(audioMgr);
   		  oceanSound.setLocation(new Point3D(0,0,0));
   		 
   		  oceanSound.setMaxDistance(50.0f);
   		  oceanSound.setMinDistance(3.0f);
   		  oceanSound.setRollOff(5.0f);
   		  System.out.println(aqua.getWorldTranslation().getCol(3));
   		 
   		  setEarParameters();
   		  
   		  oceanSound.play(100,true);
 
   	 }
   	 public void setEarParameters(){
   	      Matrix3D avDir = (Matrix3D)(player.getWorldRotation().clone());
   	      float camAz = cam1Controller.getAzimuth(true);
   	      avDir.rotateY(180.0f-camAz);
   	      Vector3D camDir = new Vector3D(0,0,1);
   	      camDir = camDir.mult(avDir);
   	      
   	      audioMgr.getEar().setLocation(new Point3D(0,0,0));
   	      audioMgr.getEar().setOrientation(camDir, new Vector3D(0,1,0));
   	  }
     
	//physics
	 private class StartAction extends AbstractInputAction{ 
	      public void performAction(float time, Event evt){ 
	    	 
	  	    ball = loader2.loadModel("./objects/cannon.obj");
	  	    //UV texture but you must map that png to the UV in maya to do so create a material then for the color load that png that you exported and edited as a UV map
	  	    TextureState cannonState;
	  	    Texture cannonTexture = TextureManager.loadTexture2D("./materials/cannon_tex.png");
	  	    cannonTexture.setApplyMode(sage.texture.Texture.ApplyMode.Replace);
	  	    cannonState = (TextureState)
	  	    display.getRenderer().createRenderState(RenderState.RenderStateType.Texture);
	  	    cannonState.setTexture(cannonTexture,0);
	  	    cannonState.setEnabled(true);
	  	    
	  	    // apply the texture to the terrain
	  	    
	         ball.setRenderState(cannonState);
	         Vector3D playerLoc =  player.getLocalTranslation().getCol(3);
	         ball.translate((float)playerLoc.getX(),(float)playerLoc.getY(), (float)playerLoc.getZ());
	         ball.scale(0.05f, 0.05f, 0.05f);
	         scene.addChild(ball);
	         ball.updateGeometricState(1.0f, true);
	         createSagePhysicsWorld();
	         
	         running = true;
	      } 
	 }
}
