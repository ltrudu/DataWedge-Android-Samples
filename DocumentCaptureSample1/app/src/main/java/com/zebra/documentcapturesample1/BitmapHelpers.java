package com.zebra.documentcapturesample1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapHelpers {

    public static Bitmap scaleAndCenterBitmap(Bitmap bitmap, double targetAspectRatio, int horizontalMargin, int verticalMargin) {
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        double bitmapAspectRatio = (double) bitmapWidth / bitmapHeight;

        int targetWidth;
        int targetHeight;

        if (bitmapAspectRatio > targetAspectRatio) {
            // Scale based on width
            targetWidth = bitmapWidth - 2 * horizontalMargin;
            targetHeight = (int) (targetWidth / targetAspectRatio);
        } else {
            // Scale based on height
            targetHeight = bitmapHeight - 2 * verticalMargin;
            targetWidth = (int) (targetHeight * targetAspectRatio);
        }

        float scale = Math.min((float) targetWidth / bitmapWidth, (float) targetHeight / bitmapHeight);

        int newWidth = (int) (bitmapWidth * scale);
        int newHeight = (int) (bitmapHeight * scale);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);

        // Center the scaled bitmap within the target dimensions with margins
        Bitmap resultBitmap = Bitmap.createBitmap(targetWidth + 2 * horizontalMargin, targetHeight + 2 * verticalMargin, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);
        int left = (targetWidth + 2 * horizontalMargin - newWidth) / 2;
        int top = (targetHeight + 2 * verticalMargin - newHeight) / 2;
        canvas.drawBitmap(scaledBitmap, left, top, null);

        return resultBitmap;
    }

    public static Bitmap concatenateBitmaps(Bitmap bitmap1, Bitmap bitmap2, int inBetweenMargin)
    {
        // Calculate the width and height for the new bitmap
        int width = bitmap1.getWidth();
        int height = bitmap1.getHeight() + bitmap2.getHeight() + inBetweenMargin;

// Create a new bitmap with the combined height
        Bitmap combinedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

// Create a canvas to draw on the new bitmap
        Canvas canvas = new Canvas(combinedBitmap);

// Draw the first bitmap at the top
        canvas.drawBitmap(bitmap1, 0f, 0f, null);

// Draw the second bitmap below the first
        canvas.drawBitmap(bitmap2, 0f, bitmap1.getHeight() + inBetweenMargin, null);

        return combinedBitmap;
    }

    public static Bitmap cropBitmap(Bitmap bitmap, int x, int y, int width, int height) {
        Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, x, y, width, height);
        return croppedBitmap;
    }

    public static void saveBitmapAsPNG(Bitmap bitmap, String path)
    {
        FileOutputStream out = null;
        try {
            File file = new File(path);
            if(file.exists())
                file.delete();

            out = new FileOutputStream(file);

            // Compress the bitmap into a PNG file with 100% quality
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap scaleBitmapWithMargins(Bitmap original, int targetWidth, int targetHeight, int margin) {
        int originalWidth = original.getWidth();
        int originalHeight = original.getHeight();

        float aspectRatio = (float) originalWidth / originalHeight;
        int scaledWidth = targetWidth - 2 * margin;
        int scaledHeight = targetHeight - 2 * margin;

        if (aspectRatio > 1) {
            scaledHeight = (int) (scaledWidth / aspectRatio);
        } else {
            scaledWidth = (int) (scaledHeight * aspectRatio);
        }

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(original, scaledWidth, scaledHeight, true);
        Bitmap bitmapWithMargins = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithMargins);
        canvas.drawColor(0xFFFFFFFF); // Optional: Fill the background with white color

        // Calculate the position to center the scaled bitmap
        int left = (targetWidth - scaledWidth) / 2;
        int top = (targetHeight - scaledHeight) / 2;

        canvas.drawBitmap(scaledBitmap, left, top, new Paint());

        return bitmapWithMargins;
    }

    public static Bitmap scaleBitmapToFill(Bitmap bitmap, int targetWidth, int targetHeight, int margin, boolean filter) {
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        float scale = Math.min((float) targetWidth / bitmapWidth, (float) targetHeight / bitmapHeight);

        int newWidth = (int) (bitmapWidth * scale) - 2 * margin;
        int newHeight = (int) (bitmapHeight * scale) - 2 * margin;

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, filter);

        // Center the scaled bitmap within the target dimensions
        Bitmap resultBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);
        int left = (targetWidth - newWidth) / 2;
        int top = (targetHeight - newHeight) / 2;
        canvas.drawBitmap(scaledBitmap, left, top, null);

        return resultBitmap;
    }

    public static Bitmap scaleBitmap_(Bitmap bitmap, int targetWidth, int targetHeight, int margin, boolean filter) {
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmapWidth, bitmapHeight, filter);
        // Center the scaled bitmap within the target dimensions
        Bitmap resultBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);
        int left = (targetWidth - bitmapWidth) / 2;
        int top = (targetHeight - bitmapHeight) / 2;
        canvas.drawBitmap(scaledBitmap, left, top, null);

        return resultBitmap;
    }


    public static Bitmap scaleBitmap(Bitmap bitmap, int targetWidth, int targetHeight, int margin, boolean filter) {
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        // Center the scaled bitmap within the target dimensions
        Bitmap resultBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);
        int left = (targetWidth - bitmapWidth) / 2;
        int top = (targetHeight - bitmapHeight) / 2;
        canvas.drawBitmap(bitmap, left, top, null);

        return resultBitmap;
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle, boolean filter) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, filter);
    }


    public static void saveBitmapAsMonochromeBMP(Bitmap bitmap, String filePath) throws IOException {
        // Convert the Bitmap to monochrome
        Bitmap monochromeBitmap = convertToMonochrome(bitmap);

        // Save the monochrome Bitmap as a BMP file
        saveAsBMP(monochromeBitmap, filePath);
    }

    public static Bitmap convertToMonochrome(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap monochromeBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = bitmap.getPixel(x, y);
                if (Color.alpha(pixel) == 0) {
                    // If the pixel is transparent, set it to white
                    monochromeBitmap.setPixel(x, y, Color.WHITE);
                } else {
                    int gray = (int) (0.299 * Color.red(pixel) + 0.587 * Color.green(pixel) + 0.114 * Color.blue(pixel));
                    int monochromePixel = gray < 128 ? Color.BLACK : Color.WHITE;
                    monochromeBitmap.setPixel(x, y, monochromePixel);
                }
            }
        }

        return monochromeBitmap;
    }

    public static void saveByteArrayAsFile(byte[] byteArray, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(byteArray);
        }
    }

    public static Bitmap addWhiteBackground(Bitmap originalBitmap) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();

        // Create a new bitmap with the same dimensions
        Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        newBitmap.setDensity(originalBitmap.getDensity());

        Canvas canvas = new Canvas(newBitmap);

        // Draw a white background
        canvas.drawColor(Color.WHITE);

        // Draw the original bitmap on top of the white background
        canvas.drawBitmap(originalBitmap, 0, 0, null);

        return newBitmap;
    }


    public static void saveAsBMP(Bitmap bitmap, String filePath) throws IOException {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int rowPadding = (4 - (width * 3) % 4) % 4;
        int imageSize = (width * 3 + rowPadding) * height;
        int fileSize = 54 + imageSize;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        // BMP Header
        dos.writeBytes("BM");
        dos.writeInt(Integer.reverseBytes(fileSize));
        dos.writeInt(0);
        dos.writeInt(Integer.reverseBytes(54));

        // DIB Header
        dos.writeInt(Integer.reverseBytes(40));
        dos.writeInt(Integer.reverseBytes(width));
        dos.writeInt(Integer.reverseBytes(height));
        dos.writeShort(Short.reverseBytes((short) 1));
        dos.writeShort(Short.reverseBytes((short) 24));
        dos.writeInt(0);
        dos.writeInt(Integer.reverseBytes(imageSize));
        dos.writeInt(0);
        dos.writeInt(0);
        dos.writeInt(0);
        dos.writeInt(0);

        // Bitmap Data
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                int pixel = bitmap.getPixel(x, y);
                dos.writeByte(Color.blue(pixel));
                dos.writeByte(Color.green(pixel));
                dos.writeByte(Color.red(pixel));
            }
            for (int p = 0; p < rowPadding; p++) {
                dos.writeByte(0);
            }
        }

        dos.flush();
        byte[] bmpData = baos.toByteArray();
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(bmpData);
        fos.close();
    }

}
