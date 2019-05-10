package com.ls.stockforecast.utils.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * ExcelSheet实体类
 * 
 */
public class SheetVo {

	// Sheet名
	private String name = null;

	// Sheet内容
	private List<CellVo> cellList = null;

	// 当前序号
	private int crrCellIndex = -1;

	// sheetNo
	private int sheetNo = 0;

	// 当前单元格序号
	private int crrMergeIndex = -1;

	// 插入新的一行
	private int[] row;

	// 插入新的一列
	private int[] col;

	/**
	 * @return the col
	 */
	public int[] getCol() {
		return col;
	}

	/**
	 * @param col
	 *            the col to set
	 */
	public void setCol(int[] col) {
		this.col = col;
	}

	/**
	 * @return the row
	 */
	public int[] getRow() {
		return row;
	}

	/**
	 * @param row
	 *            the row to set
	 */
	public void setRow(int[] row) {
		this.row = row;
	}

	/**
	 * @return the cellList
	 */
	public List<CellVo> getCellList() {
		return cellList;
	}

	/**
	 * @param cellList
	 *            the cellList to set
	 */
	public void setCellList(List<CellVo> cellList) {
		this.cellList = cellList;
	}

	/**
	 * @return the crrCellIndex
	 */
	public int getCrrCellIndex() {
		return crrCellIndex;
	}

	/**
	 * @param crrCellIndex
	 *            the crrCellIndex to set
	 */
	public void setCrrCellIndex(int crrCellIndex) {
		this.crrCellIndex = crrCellIndex;
	}

	/**
	 * @return the crrMergeIndex
	 */
	public int getCrrMergeIndex() {
		return crrMergeIndex;
	}

	/**
	 * @param crrMergeIndex
	 *            the crrMergeIndex to set
	 */
	public void setCrrMergeIndex(int crrMergeIndex) {
		this.crrMergeIndex = crrMergeIndex;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param sheetNo
	 *            the sheetNo to set
	 */
	public void setSheetNo(int sheetNo) {
		this.sheetNo = sheetNo;
	}

	/**
	 * @return the sheetNo
	 */
	public int getSheetNo() {
		return sheetNo;
	}

	/**
	 * 添加一个Cell
	 * 
	 * @param vo
	 */
	public void addCell(CellVo vo) {
		if (cellList == null) {
			cellList = new ArrayList<CellVo>();
		}
		cellList.add(vo);
	}

	/**
	 * 获取下一个Cell
	 * 
	 * @return
	 */
	public CellVo getCell() {

		if (crrCellIndex < 0) {
			crrCellIndex = 0;
		} else {
			crrCellIndex++;
		}

		if (cellList == null || cellList.size() <= 0
				|| crrCellIndex >= cellList.size()) {
			return null;
		}

		return cellList.get(crrCellIndex);
	}

	/**
	 * 获得指定位置的Cell
	 * 
	 * @param index
	 * @return
	 */
	public CellVo getCell(int index) {

		if (cellList == null || cellList.size() <= index) {
			return null;
		}

		return cellList.get(index);

	}

}
