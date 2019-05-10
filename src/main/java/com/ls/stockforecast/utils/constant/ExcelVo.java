package com.ls.stockforecast.utils.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel文件实体类
 * 
 */
public class ExcelVo {

	// 文件名
	private String fileName = null;

	// Sheet内容
	private List<SheetVo> sheetList = null;

	// SheetIndex计数
	private int crrSheetIndex = -1;

	// 要删除的sheet下标
	private int[] delSheetIndex = null;

	/**
	 * @return the delSheetIndex
	 */
	public int[] getDelSheetIndex() {
		return delSheetIndex;
	}

	/**
	 * @param delSheetIndex
	 *            the delSheetIndex to set
	 */
	public void setDelSheetIndex(int[] delSheetIndex) {
		this.delSheetIndex = delSheetIndex;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 增加一个Sheet
	 * 
	 * @param sheet
	 *            sheet
	 */
	public void addSheet(SheetVo sheet) {
		if (sheetList == null) {
			sheetList = new ArrayList<SheetVo>();
		}
		sheetList.add(sheet);
	}

	/**
	 * 增加一个Sheet到指定位置
	 * 
	 * @param sheet
	 *            sheet
	 */
	public void addSheet(SheetVo sheet, int index) {
		if (sheetList == null) {
			sheetList = new ArrayList<SheetVo>();
		}
		sheetList.add(index, sheet);
	}

	/**
	 * 获取指定位置的Sheet
	 * 
	 * @param index
	 *            位置
	 * @return sheet
	 */
	public SheetVo getSheet(int index) {

		if (sheetList == null || sheetList.size() <= index) {
			return null;
		}
		return sheetList.get(index);
	}

	/**
	 * 获取下一个的Sheet
	 * 
	 * @return sheet
	 */
	public SheetVo getSheet() {

		if (crrSheetIndex < 0) {
			crrSheetIndex = 0;

		} else {
			crrSheetIndex++;
		}
		if (sheetList == null || sheetList.size() <= 0
				|| crrSheetIndex >= sheetList.size()) {
			return null;
		}

		return sheetList.get(crrSheetIndex);

	}

	/**
	 * 清空所有Sheet数据
	 */
	public void removeAllSheet() {
		if (sheetList != null) {
			sheetList.clear();
			crrSheetIndex = -1;
		}
	}

	/**
	 * 清空指定Sheet
	 * 
	 * @param index
	 *            位置
	 */
	public void removeSheet(int index) {
		if (sheetList != null) {
			sheetList.remove(index);
			crrSheetIndex = -1;
		}
	}
}
