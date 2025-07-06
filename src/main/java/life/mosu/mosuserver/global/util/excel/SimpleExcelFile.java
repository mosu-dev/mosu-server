package life.mosu.mosuserver.global.util.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import life.mosu.mosuserver.global.exception.CustomRuntimeException;
import life.mosu.mosuserver.global.exception.ErrorCode;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class SimpleExcelFile<T> {

    private static final SpreadsheetVersion supplyExcelVersion = SpreadsheetVersion.EXCEL2007;
    private static final int ROW_START_INDEX = 0;
    private static final int COLUMN_START_INDEX = 0;

    private SXSSFWorkbook wb;
    private Sheet sheet;
    private SimpleExcelMetadata excelMetaData;

    public SimpleExcelFile(List<T> data, Class<T> type) {
        this.wb = new SXSSFWorkbook();
        this.excelMetaData = new SimpleExcelMetadata(type);
        renderExcel(data);
    }

    private void renderExcel(List<T> data) {
        sheet = wb.createSheet();
        renderHeader(sheet, ROW_START_INDEX, COLUMN_START_INDEX);

        if (data.isEmpty()) {
            return;
        }

        int rowIndex = ROW_START_INDEX + 1;
        for (Object obj : data) {
            renderBody(obj, rowIndex++, COLUMN_START_INDEX);
        }
    }

    private void renderHeader(Sheet sheet, int rowStartIndex, int columnStartIndex) {
        Row row = sheet.createRow(rowStartIndex);
        int columnIndex = columnStartIndex;
        for (Object dataFieldName : excelMetaData.getDataFieldNames()) {
            String fieldName = (String) dataFieldName;
            Cell cell = row.createCell(columnIndex);
            cell.setCellValue(excelMetaData.getExcelHeaderName(fieldName));

            sheet.setColumnWidth(columnIndex, 20 * 256);
            columnIndex++;

        }
    }

    private void renderBody(Object data, int rowStartIndex, int columnStartIndex) {
        Row row = sheet.createRow(rowStartIndex);
        int columnIndex = columnStartIndex;
        for (Object dataFieldName : excelMetaData.getDataFieldNames()) {

            String fieldName = (String) dataFieldName;
            Cell cell = row.createCell(columnIndex++);
            try {
                Field field = FieldUtils.getField(data.getClass(), (fieldName), true);
                field.setAccessible(true);

                renderCellValue(cell, field.get(data));
            } catch (Exception e) {
                throw new CustomRuntimeException(ErrorCode.EXCEL_CREATION_FAILED);
            }
        }
    }

    /**
     * 셀에 값을 렌더링
     *
     * @param cell
     * @param cellValue
     */
    private void renderCellValue(Cell cell, Object cellValue) {
        if (cellValue instanceof Number numberValue) { // (4)
            cell.setCellValue(numberValue.doubleValue());
            return;
        }
        cell.setCellValue(cellValue == null ? "" : cellValue.toString());
    }

    public void write(OutputStream stream) throws IOException {
        wb.write(stream);
        wb.close();
        stream.close();
    }
}
