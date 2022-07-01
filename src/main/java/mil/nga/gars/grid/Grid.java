package mil.nga.gars.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mil.nga.gars.GARSUtils;
import mil.nga.gars.features.GridLine;
import mil.nga.gars.property.GARSProperties;
import mil.nga.grid.BaseGrid;
import mil.nga.grid.GridStyle;
import mil.nga.grid.color.Color;
import mil.nga.grid.features.Bounds;
import mil.nga.grid.features.Point;
import mil.nga.grid.property.PropertyConstants;
import mil.nga.grid.tile.GridTile;

/**
 * Grid
 * 
 * @author osbornb
 */
public class Grid extends BaseGrid implements Comparable<Grid> {

	/**
	 * Default line width
	 */
	public static final double DEFAULT_WIDTH = GARSProperties.getInstance()
			.getDoubleProperty(PropertyConstants.GRID, PropertyConstants.WIDTH);

	/**
	 * Grid type
	 */
	private final GridType type;

	/**
	 * Grid line styles
	 */
	private Map<GridType, GridStyle> styles = new HashMap<>();

	/**
	 * Constructor
	 * 
	 * @param type
	 *            grid type
	 */
	public Grid(GridType type) {
		this.type = type;
	}

	/**
	 * Get the grid type
	 * 
	 * @return grid type
	 */
	public GridType getType() {
		return type;
	}

	/**
	 * Is the provided grid type
	 * 
	 * @param type
	 *            grid type
	 * @return true if the type
	 */
	public boolean isType(GridType type) {
		return this.type == type;
	}

	/**
	 * Get the precision in degrees
	 * 
	 * @return precision degrees
	 */
	public double getPrecision() {
		return type.getPrecision();
	}

	/**
	 * Get the grid type precision line style for the grid type
	 * 
	 * @param gridType
	 *            grid type
	 * @return grid type line style
	 */
	public GridStyle getStyle(GridType gridType) {
		GridStyle style = null;
		if (gridType == type) {
			style = getStyle();
		} else {
			style = styles.get(gridType);
		}
		return style;
	}

	/**
	 * Get the grid type line style for the grid type or create it
	 * 
	 * @param gridType
	 *            grid type
	 * @return grid type line style
	 */
	private GridStyle getOrCreateStyle(GridType gridType) {
		GridStyle style = getStyle(gridType);
		if (style == null) {
			style = new GridStyle();
			setStyle(gridType, style);
		}
		return style;
	}

	/**
	 * Set the grid type precision line style
	 * 
	 * @param gridType
	 *            grid type
	 * @param style
	 *            grid line style
	 */
	public void setStyle(GridType gridType, GridStyle style) {
		if (gridType.getPrecision() < getPrecision()) {
			throw new IllegalArgumentException(
					"Grid can not define a style for a higher precision grid type. Type: "
							+ type + ", Style Type: " + gridType);
		}
		if (gridType == type) {
			setStyle(style);
		} else {
			styles.put(gridType, style != null ? style : new GridStyle());
		}
	}

	/**
	 * Clear the propagated grid type precision styles
	 */
	public void clearPrecisionStyles() {
		styles.clear();
	}

	/**
	 * Get the grid type precision line color
	 * 
	 * @param gridType
	 *            grid type
	 * @return grid type line color
	 */
	public Color getColor(GridType gridType) {
		Color color = null;
		GridStyle style = getStyle(gridType);
		if (style != null) {
			color = style.getColor();
		}
		if (color == null) {
			color = getColor();
		}
		return color;
	}

	/**
	 * Set the grid type precision line color
	 * 
	 * @param gridType
	 *            grid type
	 * @param color
	 *            grid line color
	 */
	public void setColor(GridType gridType, Color color) {
		getOrCreateStyle(gridType).setColor(color);
	}

	/**
	 * Get the grid type precision line width
	 * 
	 * @param gridType
	 *            grid type
	 * @return grid type line width
	 */
	public double getWidth(GridType gridType) {
		double width = 0;
		GridStyle style = getStyle(gridType);
		if (style != null) {
			width = style.getWidth();
		}
		if (width == 0) {
			width = getWidth();
		}
		return width;
	}

	/**
	 * Set the grid type precision line width
	 * 
	 * @param gridType
	 *            grid type
	 * @param width
	 *            grid line width
	 */
	public void setWidth(GridType gridType, double width) {
		getOrCreateStyle(gridType).setWidth(width);
	}

	/**
	 * Get the grid labeler
	 * 
	 * @return grid labeler
	 */
	public GridLabeler getLabeler() {
		return (GridLabeler) super.getLabeler();
	}

	/**
	 * Set the grid labeler
	 * 
	 * @param labeler
	 *            grid labeler
	 */
	public void setLabeler(GridLabeler labeler) {
		super.setLabeler(labeler);
	}

	/**
	 * Get the lines for the tile
	 * 
	 * @param tile
	 *            tile
	 * @return lines
	 */
	public List<GridLine> getLines(GridTile tile) {
		return getLines(tile.getZoom(), tile.getBounds());
	}

	/**
	 * Get the lines for the zoom and tile bounds
	 * 
	 * @param zoom
	 *            zoom level
	 * @param tileBounds
	 *            tile bounds
	 * @return lines
	 */
	public List<GridLine> getLines(int zoom, Bounds tileBounds) {
		List<GridLine> lines = null;
		if (isLinesWithin(zoom)) {
			lines = getLines(tileBounds);
		}
		return lines;
	}

	/**
	 * Get the lines for the tile bounds
	 * 
	 * @param tileBounds
	 *            tile bounds
	 * @return lines
	 */
	public List<GridLine> getLines(Bounds tileBounds) {

		List<GridLine> lines = new ArrayList<>();

		double precision = getPrecision();

		tileBounds = tileBounds.toPrecision(precision);

		for (double lon = tileBounds.getMinLongitude(); lon <= tileBounds
				.getMaxLongitude(); lon = GARSUtils.nextPrecision(lon,
						precision)) {

			GridType verticalPrecision = GridType.getPrecision(lon);

			for (double lat = tileBounds.getMinLatitude(); lat <= tileBounds
					.getMaxLatitude(); lat = GARSUtils.nextPrecision(lat,
							precision)) {

				GridType horizontalPrecision = GridType.getPrecision(lat);

				Point southwest = Point.point(lon, lat);
				Point northwest = Point.point(lon, lat + precision);
				Point southeast = Point.point(lon + precision, lat);

				// Vertical line
				lines.add(
						GridLine.line(southwest, northwest, verticalPrecision));

				// Horizontal line
				lines.add(GridLine.line(southwest, southeast,
						horizontalPrecision));

			}
		}

		return lines;
	}

	/**
	 * Get the labels for the tile
	 * 
	 * @param tile
	 *            tile
	 * @return labels
	 */
	public List<GridLabel> getLabels(GridTile tile) {
		return getLabels(tile.getZoom(), tile.getBounds());
	}

	/**
	 * Get the labels for the zoom and tile bounds
	 * 
	 * @param zoom
	 *            zoom level
	 * @param tileBounds
	 *            tile bounds
	 * @return labels
	 */
	public List<GridLabel> getLabels(int zoom, Bounds tileBounds) {
		List<GridLabel> labels = null;
		if (isLabelerWithin(zoom)) {
			labels = getLabeler().getLabels(tileBounds, type);
		}
		return labels;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Grid other) {
		return Double.compare(getPrecision(), other.getPrecision());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grid other = (Grid) obj;
		if (type != other.type)
			return false;
		return true;
	}

}
