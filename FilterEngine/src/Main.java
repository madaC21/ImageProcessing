import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sPath = "C:\\Users\\Madalina\\Desktop\\ProcesareImagini_FilterEngine";
	private String sFileName = "dog-2088426_960_720.jpg";
	
	private BufferedImage img = null;

	public Main() {
		this.setPreferredSize();
		
		this.OpenFile();
		img = this.Load();
		img = this.Scale(img, this.GetSize());
	}
	
	public Main(final BufferedImage imgNew) {
		
		this.setPreferredSize();
		img = this.Scale(imgNew, this.GetSize());
	}
	
	// +++++++ MEMBER FUNCTIONS +++++++++
	
	public BufferedImage GetImage() {
		return img;
	}
	
	
	public BufferedImage Scale(final BufferedImage imgOriginal, final Dimension dim) {
		final BufferedImage imgScaled = new BufferedImage(dim.width, dim.height,BufferedImage.TYPE_INT_ARGB);
		imgScaled.getGraphics().drawImage( imgOriginal.getScaledInstance(dim.width, dim.height, BufferedImage.SCALE_SMOOTH), 0, 0, null );
		return imgScaled;
	}
	
	public BufferedImage ProcessSepia() {
		// TODO: arhitectura de procesare
		final FilterEngine filter = new FilterEngine();
		return filter.Sepia(img);
	}
	
	public BufferedImage ProcessBlur() {
		// TODO: arhitectura de procesare
		final FilterEngine filter = new FilterEngine();
		return filter.Blur2(img);
	}
	
	public BufferedImage ProcessBlackWhite() {
		// TODO: arhitectura de procesare
		final FilterEngine filter = new FilterEngine();
		return filter.BlackWhite(img);
	}
	
	
	public BufferedImage ProcessNegative() {
		// TODO: arhitectura de procesare
		final FilterEngine filter = new FilterEngine();
		return filter.Negative(img);
	}
	
	
	
	// +++ Functii IO +++
	
	public void OpenFile() {
		final JFileChooser chooser = new JFileChooser();
        final FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & GIF Images", "jpg", "gif");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File(sPath));
    	System.out.println(chooser.getCurrentDirectory());

        final int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getName());
            sPath = chooser.getSelectedFile().getAbsoluteFile().getParent();
            sFileName = chooser.getSelectedFile().getName();
        }
	}

	public BufferedImage Load() {
		return this.Load(sPath + "\\" + sFileName);
	}
	public BufferedImage Load(final String sFileName) {
		final File file = new File(sFileName);

		try {
			final BufferedImage img = ImageIO.read(file);
			return img;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
	}

	public void setPreferredSize() {
		this.setPreferredSize(this.GetSize());
	}
	
	public Dimension GetSize() {
		return new Dimension(600, 450);
	}
	
	public JFrame CreateWindow(final String sTitlu) {
		final JFrame frame = new JFrame(sTitlu);
		frame.getContentPane().add(this, "Center");
		frame.setSize(this.getPreferredSize());
		frame.setVisible(true);
		return frame;
	}
	
	// ++++++++++++ MAIN ++++++++++++

	public static void main(String[] args) {
		final Main mainEntry = new Main();
		mainEntry.CreateWindow("Original");
	
		Main entry3 = new Main(mainEntry.ProcessBlur());
		entry3.CreateWindow("Blur");
		
		Main entry2 = new Main(mainEntry.ProcessSepia());
		entry2.CreateWindow("Sepia");
		
		Main entry4 = new Main(mainEntry.ProcessBlackWhite());
		entry4.CreateWindow("BlackWhite");
		
		
		Main entry5 = new Main(mainEntry.ProcessNegative());
		entry5.CreateWindow("Negative");
		
		
		
	}

}
