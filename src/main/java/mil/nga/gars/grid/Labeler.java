package mil.nga.gars.grid;

import java.util.List;

import mil.nga.gars.color.Color;
import mil.nga.gars.features.Bounds;
import mil.nga.gars.property.GARSProperties;
import mil.nga.gars.property.PropertyConstants;

/**
 * Grid Labeler
 * 
 * @author osbornb
 */
public abstract class Labeler {

	/**
	 * Default text size
	 */
	public static final double DEFAULT_TEXT_SIZE = GARSProperties
			.getDoubleProperty(PropertyConstants.LABELER,
					PropertyConstants.TEXT_SIZE);

	/**
	 * Default buffer size
	 */
	public static final double DEFAULT_BUFFER = GARSProperties
			.getDoubleProperty(PropertyConstants.LABELER,
					PropertyConstants.BUFFER);

	/**
	 * Enabled labeler
	 */
	private boolean enabled;

	/**
	 * Minimum zoom level
	 */
	private int minZoom;

	/**
	 * Maximum zoom level
	 */
	private Integer maxZoom;

	/**
	 * Label color
	 */
	private Color color;

	/**
	 * Label text size
	 */
	private double textSize;

	/**
	 * Grid zone edge buffer (greater than or equal to 0.0 and less than 0.5)
	 */
	private double buffer;

	/**
	 * Default Constructor
	 */
	public Labeler() {
		enabled = true;
		minZoom = 0;
		color = Color.black();
		textSize = DEFAULT_TEXT_SIZE;
		buffer = DEFAULT_BUFFER;
	}

	/**
	 * Constructor
	 * 
	 * @param minZoom
	 *            minimum zoom
	 * @param color
	 *            label color
	 */
	public Labeler(int minZoom, Color color) {
		this(minZoom, color, DEFAULT_TEXT_SIZE);
	}

	/**
	 * Constructor
	 * 
	 * @param minZoom
	 *            minimum zoom
	 * @param color
	 *            label color
	 * @param textSize
	 *            label text size
	 */
	public Labeler(int minZoom, Color color, double textSize) {
		this(minZoom, color, textSize, DEFAULT_BUFFER);
	}

	/**
	 * Constructor
	 * 
	 * @param minZoom
	 *            minimum zoom
	 * @param color
	 *            label color
	 * @param textSize
	 *            label text size
	 * @param buffer
	 *            grid zone edge buffer (greater than or equal to 0.0 and less
	 *            than 0.5)
	 */
	public Labeler(int minZoom, Color color, double textSize, double buffer) {
		this(minZoom, null, color, textSize, buffer);
	}

	/**
	 * Constructor
	 * 
	 * @param minZoom
	 *            minimum zoom
	 * @param maxZoom
	 *            maximum zoom
	 * @param color
	 *            label color
	 */
	public Labeler(int minZoom, Integer maxZoom, Color color) {
		this(minZoom, maxZoom, color, DEFAULT_TEXT_SIZE);
	}

	/**
	 * Constructor
	 * 
	 * @param minZoom
	 *            minimum zoom
	 * @param maxZoom
	 *            maximum zoom
	 * @param color
	 *            label color
	 * @param textSize
	 *            label text size
	 */
	public Labeler(int minZoom, Integer maxZoom, Color color, double textSize) {
		this(minZoom, maxZoom, color, textSize, DEFAULT_BUFFER);
	}

	/**
	 * Constructor
	 * 
	 * @param minZoom
	 *            minimum zoom
	 * @param maxZoom
	 *            maximum zoom
	 * @param color
	 *            label color
	 * @param textSize
	 *            label text size
	 * @param buffer
	 *            grid zone edge buffer (greater than or equal to 0.0 and less
	 *            than 0.5)
	 */
	public Labeler(int minZoom, Integer maxZoom, Color color, double textSize,
			double buffer) {
		this(true, minZoom, maxZoom, color, textSize, buffer);
	}

	/**
	 * Constructor
	 * 
	 * @param enabled
	 *            enabled labeler
	 * @param minZoom
	 *            minimum zoom
	 * @param maxZoom
	 *            maximum zoom
	 * @param color
	 *            label color
	 */
	public Labeler(boolean enabled, int minZoom, Integer maxZoom, Color color) {
		this(enabled, minZoom, maxZoom, color, DEFAULT_TEXT_SIZE);
	}

	/**
	 * Constructor
	 * 
	 * @param enabled
	 *            enabled labeler
	 * @param minZoom
	 *            minimum zoom
	 * @param maxZoom
	 *            maximum zoom
	 * @param color
	 *            label color
	 * @param textSize
	 *            label text size
	 */
	public Labeler(boolean enabled, int minZoom, Integer maxZoom, Color color,
			double textSize) {
		this(enabled, minZoom, maxZoom, color, textSize, DEFAULT_BUFFER);
	}

	/**
	 * Constructor
	 * 
	 * @param enabled
	 *            enabled labeler
	 * @param minZoom
	 *            minimum zoom
	 * @param maxZoom
	 *            maximum zoom
	 * @param color
	 *            label color
	 * @param textSize
	 *            label text size
	 * @param buffer
	 *            grid zone edge buffer (greater than or equal to 0.0 and less
	 *            than 0.5)
	 */
	public Labeler(boolean enabled, int minZoom, Integer maxZoom, Color color,
			double textSize, double buffer) {
		this.enabled = enabled;
		this.minZoom = minZoom;
		this.maxZoom = maxZoom;
		this.color = color;
		this.textSize = textSize;
		this.buffer = buffer;
	}

	/**
	 * Get labels for the bounds
	 * 
	 * @param tileBounds
	 *            tile bounds
	 * @param gridType
	 *            grid type
	 * @return labels
	 */
	public abstract List<Label> getLabels(Bounds tileBounds, GridType gridType);

	/**
	 * Is the grid enabled
	 * 
	 * @return enabled flag
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Set the enabled flag
	 * 
	 * @param enabled
	 *            enabled flag
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Get the minimum zoom level
	 * 
	 * @return minimum zoom level
	 */
	public int getMinZoom() {
		return minZoom;
	}

	/**
	 * Set the minimum zoom level
	 * 
	 * @param minZoom
	 *            minimum zoom level
	 */
	public void setMinZoom(int minZoom) {
		this.minZoom = minZoom;
	}

	/**
	 * Get the maximum zoom level
	 * 
	 * @return maximum zoom level
	 */
	public Integer getMaxZoom() {
		return maxZoom;
	}

	/**
	 * Has a maximum zoom level
	 * 
	 * @return true if has a maximum, false if unbounded
	 */
	public boolean hasMaxZoom() {
		return maxZoom != null;
	}

	/**
	 * Set the maximum zoom level
	 * 
	 * @param maxZoom
	 *            maximum zoom level
	 */
	public void setMaxZoom(Integer maxZoom) {
		this.maxZoom = maxZoom;
	}

	/**
	 * Is the zoom level within the grid zoom range
	 * 
	 * @param zoom
	 *            zoom level
	 * @return true if within range
	 */
	public boolean isWithin(int zoom) {
		return zoom >= minZoom && (maxZoom == null || zoom <= maxZoom);
	}

	/**
	 * Get the label color
	 * 
	 * @return label color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Set the label color
	 * 
	 * @param color
	 *            label color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Get the label text size
	 * 
	 * @return label text size
	 */
	public double getTextSize() {
		return textSize;
	}

	/**
	 * Set the label text size
	 * 
	 * @param textSize
	 *            label text size
	 */
	public void setTextSize(double textSize) {
		this.textSize = textSize;
	}

	/**
	 * Get the grid zone edge buffer
	 * 
	 * @return buffer (greater than or equal to 0.0 and less than 0.5)
	 */
	public double getBuffer() {
		return buffer;
	}

	/**
	 * Set the grid zone edge buffer
	 * 
	 * @param buffer
	 *            buffer (greater than or equal to 0.0 and less than 0.5)
	 */
	public void setBuffer(double buffer) {
		if (buffer < 0.0 || buffer >= 0.5) {
			throw new IllegalArgumentException(
					"Grid zone edge buffer must be >= 0 and < 0.5. buffer: "
							+ buffer);
		}
		this.buffer = buffer;
	}

}
