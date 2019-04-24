import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.*; 
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;
 
/* Generates png and jpeg representations of a mario level from its associated .txt file */
public class ImageGen {
 
    public static void main(String[] args) throws IOException {
 
    	//Default directory to select levels from set here:
    	String defaultDir = "C:\\Users\\Ollie\\Documents\\MSc Studying\\Project\\Eclipse Projects\\MarioLevelComp2011\\Data\\mario\\levels";
    	
    	//Default directory to create level representations set here:
    	String defaultOutputDir = "C:\\Users\\Ollie\\Desktop\\";
    	
    	//Instantiate storage for the level file to generate an image of
    	File file = null;
    	String fileName = null;
    	
    	//Logic for running a basic file selector
        JFileChooser fileChooser = new JFileChooser(defaultDir);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
        		"TEXT FILES", "txt", "text");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            //Store selected file
            file = fileChooser.getSelectedFile();
            //Store selected file name for output naming
            fileName = fileChooser.getSelectedFile().getName();
        }
    	
    	BufferedReader br = new BufferedReader(new FileReader(file)); 
    	  
    	String st; 
    	
    	int height = 0;
    	int width = 0;
    	
    	//Set block size in pixels
    	int blockSize = 10;
    	
    	//Get level height
    	while ((st = br.readLine()) != null) {
    		 System.out.println(st); 
    		 height+=1;
    	} 
    	
    	System.out.println("Level height: " + height);
    	
    	//Refresh buffer
    	br = new BufferedReader(new FileReader(file)); 
    	st = br.readLine();
    	
    	//Get level width
    	for (int i = 0; i < st.length(); i++) {
    		width+=1;
    	}
    	
    	System.out.println("Level width: " + width);
    	 		
    	// Constructs a BufferedImage of one of the predefined image types.
    	BufferedImage bufferedImage = new BufferedImage((width*blockSize), (height*blockSize), BufferedImage.TYPE_INT_RGB);
    	 
    	// Create a graphics which can be used to draw into the buffered image
    	Graphics2D g2d = bufferedImage.createGraphics();
    	
    	//Paint the full image light blue
    	g2d.setColor(new Color(191, 252, 252));
    	g2d.fillRect(0, 0, (width*blockSize), (height*blockSize));
    	
    	//Refresh buffer
    	br = new BufferedReader(new FileReader(file));
    	
    	//Instantiate block characters to check
    	char blockChar = 'X';
    	char lPipeChar = '[';
    	char rPipeChar = ']';
    	char lTopPipeChar = '<';
    	char rTopPipeChar = '>';
    	char qBlockChar = 'Q';
    	char smashBlockChar = 'S';
    	
    	//Paint image based on file contents
    	for (int i = 0; i < height; i++) {
    		st = br.readLine();
        	for (int j = 0; j < st.length(); j++) {
        		      		        	   		
        		if ((st.charAt(j) == blockChar)||(st.charAt(j) == qBlockChar)||(st.charAt(j) == smashBlockChar)) {
        			//Paint all solid blocks brown
        			g2d.setColor(new Color(112, 71, 25));
            		g2d.fillRect((j* blockSize), (i * blockSize), blockSize, blockSize);
            		
            		//If it is a question block, write a ?
            		//NB: It writes the ? above the block unless +1 added to y axis
            		//I am also adding 2 in the x direction to better centre the ?. This is a complete hack
            		if (st.charAt(j) == qBlockChar) {
            	    	g2d.setColor(Color.yellow);
            	    	g2d.drawString("?", (j* blockSize) + 2, ((i+1) * blockSize));
            		}
        		}
        		else if ((st.charAt(j) == lPipeChar)||(st.charAt(j) == rPipeChar)) {
        			
        			g2d.setColor(new Color(10, 173, 1));
            		g2d.fillRect((j* blockSize), (i * blockSize), blockSize, blockSize);
        		}
        		else if ((st.charAt(j) == lTopPipeChar)||(st.charAt(j) == rTopPipeChar)) {
        			
        			g2d.setColor(new Color(6, 114, 0));
            		g2d.fillRect((j* blockSize), (i * blockSize), blockSize, blockSize);
        		}       		       		       		
        	}
    	} 
    	  
    	// Release used system resources 
    	g2d.dispose();
    	
    	//Remove file extension from file name
    	fileName = fileName.replace(".txt", "");
    	 
    	// Save as PNG
    	File imageFile = new File(defaultOutputDir+fileName+"PNG.png");
    	ImageIO.write(bufferedImage, "png", imageFile);
    	 
    	// Save as JPEG
    	imageFile = new File(defaultOutputDir+fileName+"JPG.jpg");
    	ImageIO.write(bufferedImage, "jpg", imageFile);
    	
    }   
}
