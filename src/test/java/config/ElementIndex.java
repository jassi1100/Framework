package config;

import org.openqa.selenium.WebElement;
/**
 * Class to have mapping between Element and index of element amongst the link.
 * @author jkhanuja
 */
public class ElementIndex {
	WebElement element = null;
	int index;
	
	/**
	 * Constructor to initialize element and index of element.
	 * @param element
	 * @param index
	 */
	public ElementIndex(WebElement element, int index){
		this.element = element;
		this.index = index;
	}
	
	/**
	 * Returns the element.
	 * @return
	 */
	public WebElement getElement() {
		return element;
	}
	
	/**
	 * Returns the index of element.
	 * @return
	 */
	public int getIndex() {
		return index;
	}
	
}
