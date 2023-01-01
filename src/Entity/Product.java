package Entity;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Product 
{
	private int ProductAmount = 1;
	private Integer ProductCode;
	private String ProductName, ProductDescription, ProductSrc;
	private Double ProductPrice;
	public File PicturePhoto;
	
	private final String dirPictures = "./EkrutAplication";
	
	public Product(Integer ProductCode,String ProductName,String ProductDescription,String ProductSrc,Double ProductPrice)
	{
		this.ProductCode = ProductCode;
		this.ProductName = ProductName;
		this.ProductDescription = ProductDescription;
		this.ProductSrc = ProductSrc;
		this.ProductPrice = ProductPrice;
		File newDir = new File(dirPictures);
		newDir.mkdirs();
		this.PicturePhoto = new File(dirPictures + "/" + this.ProductSrc);
	}
	public String getPathPicture()
	{
		return PicturePhoto.getAbsolutePath();
	}
	public Product(Product Oldproduct)
	{
		this(Oldproduct.ProductCode,
			Oldproduct.getProductName(),
			Oldproduct.getProductDescription(),
			Oldproduct.getProductSrc(),
			Oldproduct.getProductPrice());
		this.setAmount(Oldproduct.getProductAmount());
	}
	public int getProductAmount() {
		return ProductAmount;
	}
	public String getProductName() {
		return ProductName;
	}
	public String getProductDescription() {
		return ProductDescription;
	}
	public String getProductSrc() {
		return ProductSrc;
	}
	public Double getProductPrice() {
		return ProductPrice;
	}
	public void setAmount(int Amount)
	{
		ProductAmount = Amount;
	}
	@Override
	public int hashCode() {
		return Objects.hash(ProductCode);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Product))
			return false;
		Product other = (Product) obj;
		return Objects.equals(ProductCode, other.ProductCode);
	}
}
