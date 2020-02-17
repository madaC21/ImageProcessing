
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;


public class FilterEngine {
	
	public float [] GetKernel() {
		final float [] mBlur =
				this.Normalize(
				new float [] {
				2, 1, 2, 1, 2,
				1, 1, 1, 1, 1,
				2, 1, 2, 1, 2
		});
		
		return mBlur;
	}
	

	public BufferedImage Blur2(final BufferedImage img) {
		// implementare manuala a filtrului Blur
		final float [] mBlur = this.GetKernel();
		final int height = 3;
		final int width = 5;
		final int offsetRow = height / 2;
		final int offsetCol = width / 2;
		final BufferedImage imgCopy = this.Copy(img);
		
		for (int row = 0; row < imgCopy.getHeight() - height; ++row) {
			for (int col = 0; col < imgCopy.getWidth() - width; ++col) {
				
				float sumR = 0;
				float sumG = 0;
				float sumB = 0;
				
				for (int npos = 0; npos < mBlur.length; ++npos) {
					final int relativeRow = row + npos / width;
					final int relativeCol = col + npos % height;
					final int iRGB = imgCopy.getRGB(relativeCol, relativeRow);
					// citim doar byte-ul specific canalului R, G sau B
					sumR += mBlur[npos] * ( (iRGB >> 16) & 0xFF);
					sumG += mBlur[npos] * ( (iRGB >>  8) & 0xFF);
					sumB += mBlur[npos] * ( (iRGB      ) & 0xFF);
				}
				final int iSum = ((int) sumR) << 16 | ((int) sumG) << 8 | ((int) sumB) | 0xFF00_0000;
				imgCopy.setRGB(col + offsetCol, row + offsetRow, iSum);
			}
		}
		
		return imgCopy;
	}
	
	public BufferedImage Sepia(final BufferedImage img) {
        // get width and height of the image 
        int width = img.getWidth(); 
        int height = img.getHeight(); 
        final BufferedImage imgCopy = this.Copy(img);
        //convert to sepia 
        for(int y = 0; y < height; y++) 
        { 
            for(int x = 0; x < width; x++) 
            { 
                int p = img.getRGB(x,y); 
  
                int a = (p>>24)&0xff; 
                int R = (p>>16)&0xff; 
                int G = (p>>8)&0xff; 
                int B = p&0xff; 
  
                //calculate newRed, newGreen, newBlue 
                int newRed = (int)(0.393*R + 0.769*G + 0.189*B); 
                int newGreen = (int)(0.349*R + 0.686*G + 0.168*B); 
                int newBlue = (int)(0.272*R + 0.534*G + 0.131*B); 
  
                //check condition 
                if (newRed > 255) 
                    R = 255; 
                else
                    R = newRed; 
  
                if (newGreen > 255) 
                    G = 255; 
                else
                    G = newGreen; 
  
                if (newBlue > 255) 
                    B = 255; 
                else
                    B = newBlue; 
  
                //set new RGB value 
                p = (a<<24) | (R<<16) | (G<<8) | B; 
  
                imgCopy.setRGB(x, y, p); 
            } 
        } 
  
        return imgCopy;
	}
	
	
	

	public BufferedImage BlackWhite(final BufferedImage img) {

        int width = img.getWidth(); 
        int height = img.getHeight(); 
        final BufferedImage imgCopy = this.Copy(img);
        

	    //convert to grayscale
	    for(int y = 0; y < height; y++){
	      for(int x = 0; x < width; x++){
	        int p = img.getRGB(x,y);

	        int a = (p>>24)&0xff;
	        int r = (p>>16)&0xff;
	        int g = (p>>8)&0xff;
	        int b = p&0xff;

	        //calculate average
	        int avg = (r+g+b)/3;

	        //replace RGB value with avg
	        p = (a<<24) | (avg<<16) | (avg<<8) | avg;

	        imgCopy.setRGB(x, y, p);
	      }
	    }
	    return imgCopy;
	}
	
	
	public BufferedImage Negative(final BufferedImage img) {

        int width = img.getWidth(); 
        int height = img.getHeight(); 
        final BufferedImage imgCopy = this.Copy(img);

	    for(int y = 0; y < height; y++){
	      for(int x = 0; x < width; x++){
	        int p = img.getRGB(x,y);
	        
	        int a = (p>>24)&0xff;
	        int r = (p>>16)&0xff;
	        int g = (p>>8)&0xff;
	        int b = p&0xff;
	        //subtract RGB from 255
	        r = 255 - r;
	        g = 255 - g;
	        b = 255 - b;
	        //set new RGB value
	        p = (a<<24) | (r<<16) | (g<<8) | b;
	        imgCopy.setRGB(x, y, p);
	      }
	    }

	    return imgCopy;
	}
	
	
	
	
	// +++ functii helper +++
	
	public float [] Normalize(final float [] mBlur) {
		int sum = 0;
		for(final float fVal : mBlur) {
			sum += fVal;
		}
		
		for(int npos=0; npos < mBlur.length; npos++) {
			mBlur[npos] /= sum;
		}
		return mBlur;
	}
	
	public BufferedImage Copy(final BufferedImage imgOriginal) {
		final BufferedImage imgCopy = new BufferedImage(
				imgOriginal.getWidth(), imgOriginal.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		imgCopy.getGraphics().drawImage(imgOriginal, 0, 0, null);
		return imgCopy;
	}
}
