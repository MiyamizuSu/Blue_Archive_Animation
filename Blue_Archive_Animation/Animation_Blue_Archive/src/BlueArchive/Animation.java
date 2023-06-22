package BlueArchive;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Animation extends Application{
	private ArrayList<ParallelTransition> pts=new ArrayList<ParallelTransition>();
	@Override
	public void start(Stage arg0) throws Exception {
		URL url=this.getClass().getClassLoader().getResource("Music/2.mp3");
		Image imageBackGround=new Image("file:Resources/1.jpg");
		ImageView imageViewB=new ImageView(imageBackGround);
//		MotionBlur mb=new MotionBlur();
		//mb.setRadius(10);
//			mb.setAngle(90);
		imageViewB.setEffect(new GaussianBlur());
//		Media media=new Media("file:///C:/Users/80510/Desktop/1.mp3");
		Media media=new Media(url.toExternalForm());
		MediaPlayer mediaPlayer=new MediaPlayer(media);
		mediaPlayer.setVolume(0.3);
		Stage stage=new Stage();
		StackPane stackpane=new StackPane();
		AnchorPane root =new AnchorPane();
		root.getChildren().add(stackpane);
		Scene scene=new Scene(root);
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.centerOnScreen();
		stage.setFullScreenExitHint("");
		stage.setTitle("Blue Archive");
		stage.show();
		Node flowerView=this.getFlowerView(25, scene.getWidth(), scene.getHeight(), 2500);
		stackpane.getChildren().addAll(imageViewB,flowerView);
//		KeyCombination key=new KeyCodeCombination(KeyCode.ENTER,KeyCombination.CONTROL_DOWN);
		
				pts.forEach(new Consumer<ParallelTransition>() {
					@Override
					public void accept(ParallelTransition t) {
						t.setOnFinished(back->{
							mediaPlayer.play();
						});
						t.play();
						mediaPlayer.play();
					}
			});
		mediaPlayer.setOnEndOfMedia(() -> {
		    mediaPlayer.play();
		});
		
//		imageViewB.setFitHeight(scene.getHeight());
//		imageViewB.setFitWidth(scene.getWidth());
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	public Node getFlowerView(int number,double x,double y,double z) {
		ArrayList<ImageView> ivs=new ArrayList<ImageView>();
		Random random=new Random();
		double index_x;
		double index_y;
		double index_z;
		for(int i=0;i<number;i++) {
			Image image=new Image("C:\\Users\\80510\\Desktop\\Sakura(1).png");
			ImageView iv1=new ImageView(image); 
			if(random.nextBoolean()==true) {
				index_x=random.nextDouble(x)+random.nextDouble(300)+300;
			}
			else {
				index_x=random.nextDouble(x)-random.nextDouble(300)-300;
			}
			index_y=random.nextDouble(50);
			index_z=random.nextDouble(z);
			iv1.setTranslateX(index_x);
			iv1.setTranslateY(index_y);
			iv1.setTranslateZ(index_z);
			iv1.setPreserveRatio(true);
			ivs.add(iv1);
		}
		AnchorPane ap=new AnchorPane();
		ap.getChildren().addAll(ivs);
		SubScene sbc=new SubScene(ap, x, y,true,SceneAntialiasing.BALANCED);
		PerspectiveCamera camera=new PerspectiveCamera();
		sbc.setCamera(camera);
		ivs.forEach(new Consumer<ImageView>() {
			@Override
			public void accept(ImageView t) {
				double time =random.nextDouble()*10+5;
				TranslateTransition tt=new TranslateTransition(Duration.seconds(time));
				tt.setFromX(t.getTranslateX());
				tt.setFromY(t.getTranslateY());
				tt.setByX(400);
				tt.setByY(1300);
				RotateTransition rot=new RotateTransition(Duration.seconds(time));
				rot.setFromAngle(0);
				rot.setToAngle(360);
				FadeTransition appear=new FadeTransition(Duration.seconds(time/2));
				appear.setFromValue(0);
				appear.setToValue(1);
				FadeTransition disappear=new FadeTransition(Duration.seconds(3));
				disappear.setFromValue(1);
				disappear.setToValue(0);
				SequentialTransition seq=new SequentialTransition();
				seq.getChildren().addAll(appear,disappear);
				ParallelTransition para=new ParallelTransition();
				para.setNode(t);
				para.getChildren().addAll(tt,seq,rot);
				para.setCycleCount(javafx.animation.Animation.INDEFINITE);
				pts.add(para);
			}
		});
		return sbc;
	}
}
