package com.mrli.second_shop.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

@Slf4j
public class PictureUtil {

    private String destFile;
    private int width;
    private int height;
    private BufferedImage img;
    private String ext;
    private static String basePath = "";

    static {
        basePath = PictureUtil.class.getResource("/static/images/goods").getPath();
    }

    //缩放图片工具的构造函数
    public PictureUtil(String srcFile) throws IOException {
        //得到最后一个.的位置
        int index = srcFile.lastIndexOf(".");
        //获取被缩放的图片的格式
        this.ext = srcFile.substring(index + 1);
        //获取目标路径(和原始图片路径相同,在文件名后添加了一个_s)
        this.destFile = srcFile.substring(0, index) + "_s." + ext;
        //读取图片,返回一个BufferedImage对象
        this.img = ImageIO.read(new File(srcFile));
        //获取图片的长和宽
        this.width = img.getWidth();
        this.height = img.getHeight();
    }

    public static void cut(String srcpath,String subpath,int x,int y,int width,int height) throws IOException {

        FileInputStream is = null ;
        ImageInputStream iis =null ;

        try{
            //读取图片文件
            is = new FileInputStream(srcpath);

            /**//*
             * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader
             * 声称能够解码指定格式。参数：formatName - 包含非正式格式名称 .
             *（例如 "jpeg" 或 "tiff"）等。
             */
            String suffix = srcpath.substring(srcpath.lastIndexOf(".")+1);


            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(suffix);
            ImageReader reader = it.next();
            //获取图片流
            iis = ImageIO.createImageInputStream(is);

            /**//*
             * <p>iis:读取源.true:只向前搜索 </p>.将它标记为‘只向前搜索’。
             * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader
             *  避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
             */
            reader.setInput(iis,true) ;

            /**//*
             * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O
             * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件
             * 将从其 ImageReader 实现的 getDefaultReadParam 方法中返回
             * ImageReadParam 的实例。
             */
            ImageReadParam param = reader.getDefaultReadParam();

            /**//*
             * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
             * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
             */
            Rectangle rect = new Rectangle(x, y, width, height);


            //提供一个 BufferedImage，将其用作解码像素数据的目标。
            param.setSourceRegion(rect);

            /**//*
             * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将
             * 它作为一个完整的 BufferedImage 返回。
             */
            BufferedImage bi = reader.read(0,param);

            //保存新图片
            ImageIO.write(bi, "jpg", new File(subpath));
        }

        finally{
            if(is!=null)
                is.close() ;
            if(iis!=null)
                iis.close();
        }

    }

    /**
     * 赋值文件
     * @param tempPath
     * @return url
     */
    public static String copyFile(String tempPath) throws IOException {
        String tempBanner = PictureUtil.class.getResource(tempPath).getPath();
        File bannerPic = new File(tempBanner);
        //得到最后一个.的位置
        int index = tempPath.lastIndexOf(".");
        //获取被缩放的图片的格式
        String theExt = tempPath.substring(index + 1);
        String fileName = bannerPic.getName() + "." + theExt;
        String filePath = basePath + "/" + fileName;
        FileUtils.copyFile(bannerPic, new File(filePath));
        String url = "http://localhost/images/goods/" + fileName;
        return url;
    }

    /**
     * 按比例对图片进行缩放.
     * @param scale 缩放比例
     * @throws IOException
     */
    public void zoomByScale(double scale) throws IOException {
        //获取缩放后的长和宽
        int _width = (int) (scale * width);
        int _height = (int) (scale * height);
        //获取缩放后的Image对象
        Image _img = img.getScaledInstance(_width, _height, Image.SCALE_DEFAULT);
        //新建一个和Image对象相同大小的画布
        BufferedImage image = new BufferedImage(_width, _height, BufferedImage.TYPE_INT_RGB);
        //获取画笔
        Graphics2D graphics = image.createGraphics();
        //将Image对象画在画布上,最后一个参数,ImageObserver:接收有关 Image 信息通知的异步更新接口,没用到直接传空
        graphics.drawImage(_img, 0, 0, null);
        //释放资源
        graphics.dispose();
        //使用ImageIO的方法进行输出,记得关闭资源
        OutputStream out = new FileOutputStream(destFile);
        ImageIO.write(image, ext, out);
        out.close();
    }

    /**
     * 指定长和宽对图片进行缩放
     * @param width 长
     * @param height 宽
     * @throws IOException
     */
    public void zoomBySize(int width, int height) throws IOException {
        //与按比例缩放的不同只在于,不需要获取新的长和宽,其余相同.
        Image _img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(_img, 0, 0, null);
        graphics.dispose();
        OutputStream out = new FileOutputStream(destFile);
        ImageIO.write(image, ext, out);
        out.close();
    }

    /**
     * 指定长和宽对图片进行缩放
     * @param width 长
     * @param height 宽
     * @throws IOException
     */
    public static String zoomBySize(String path, int width, int height) throws IOException {
        File srcFile = new File(path);
        //得到最后一个.的位置
        int index = path.lastIndexOf(".");
        //获取被缩放的图片的格式
        String theExt = path.substring(index + 1);
        String fileName = srcFile.getName() + "_" + width + "x" + height + "." + theExt;
        //获取目标路径
        String dest = basePath + "//" + fileName ;
        log.info(dest);
        //读取图片,返回一个BufferedImage对象
        BufferedImage theImg = ImageIO.read(srcFile);
        //与按比例缩放的不同只在于,不需要获取新的长和宽,其余相同.
        Image _img = theImg.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(_img, 0, 0, null);
        graphics.dispose();
        OutputStream out = new FileOutputStream(dest);
        ImageIO.write(image, theExt, out);
        out.close();
        String url = "http://localhost/images/goods/" + fileName;
        return url;
    }


    public static void main(String[] args) throws IOException {
        String path = "D:\\upload\\goods\\mate30p4.png";
        zoomBySize(path, 800, 800);
    }
}
