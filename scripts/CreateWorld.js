var JavaPackages = new JavaImporter
(
	//Packages.gameEngine.*;,
	Packages.graphicslib3D.Point3D,
	Packages.Packages.java.awt.Color,
	Packages.java.awt.DisplayMode,
	Packages.java.io.IOException,
	Packages.java.net.InetAddress,
	//Packages.java.net.InetAddress.*;,
	Packages.java.net.UnknownHostException,
	Packages.java.text.DecimalFormat,
	Packages.java.util.Random,
	Packages.java.util.Scanner,
	Packages.sage.app.BaseGame,
	Packages.sage.networking.IGameConnection.ProtocolType,
	Packages.sage.renderer.IRenderer,
	//Packages.sage.scene.*;,
	//Packages.sage.scene.shape.*;,
	Packages.sage.texture.Texture,
	Packages.sage.texture.Texture.ApplyMode,
	Packages.sage.texture.TextureManager,
	Packages.sage.camera.ICamera,
	Packages.sage.camera.JOGLCamera,
	//Packages.sage.display.*;,
	//Packages.sage.event.*;,
	Packages.sage.input.IInputManager,
	Packages.net.java.games.input.Controller,
	Packages.java.awt.Color,
 Packages.sage.scene.Group,
 Packages.sage.scene.shape.Line
 
)
with(JavaPackages)
{
	//var rootNode = new Group();
			var rootNode = new Group();
		// creates axisscene = new Group("Root Node");
		// construct a skybox for the scene
		
		
		/*var lines = new CreateLines();
		
		scene.addChild(lines.getXAxis());
		scene.addChild(lines.getYAxis());
		scene.addChild(lines.getZAxis());*/
	var origin = new Point3D(0,0,0);	
	var xEnd = new Point3D(20,0,0);
	var yEnd = new Point3D(0,20,0);
	var zEnd = new Point3D(0,0,20);
	var xAxis = new Line(origin, xEnd, Color.red, 3);
	rootNode.addChild(xAxis);
	var yAxis = new Line(origin, yEnd, Color.green, 3);
	rootNode.addChild(yAxis);
	var zAxis = new Line(origin, zEnd, Color.blue, 3);
	rootNode.addChild(zAxis); 
		
}