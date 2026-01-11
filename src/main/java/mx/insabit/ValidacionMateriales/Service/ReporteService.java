
package mx.insabit.ValidacionMateriales.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import mx.insabit.ValidacionMateriales.DTO.MaterialResumenDTO;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class ReporteService {

    private final MaterialService materialService;

    public ReporteService(MaterialService materialService) {
        this.materialService = materialService;
    }

    public byte[] generarReporteInventarioExcel() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Inventario");

        crearTitulo(workbook, sheet);
        crearFecha(workbook, sheet);
        crearEncabezados(workbook, sheet);

        // Obtener datos desde el service de materiales (DTO)
        List<MaterialResumenDTO> materiales = materialService.listarResumen();

        // Llenar datos a partir de la fila 5 (índice 4)
        int rowNum = 4;
        for (MaterialResumenDTO m : materiales) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(m.getId() != null ? m.getId() : 0);
            row.createCell(1).setCellValue(m.getClave() != null ? m.getClave() : ""); // Aquí usas getNombre()
            row.createCell(2).setCellValue(m.getDescripcion() != null ? m.getDescripcion() : "");
            row.createCell(3).setCellValue(m.getUnidadMedida() != null ? m.getUnidadMedida() : "");
            row.createCell(4).setCellValue(m.getCantidad() != null ? m.getCantidad() : 0);

            BigDecimal precio = m.getPrecioUnitario();
            if (precio != null) {
                row.createCell(5).setCellValue(precio.doubleValue());
            } else {
                row.createCell(5).setCellValue(0.0);
            }

            row.createCell(6).setCellValue(m.getEntradas() != null ? m.getEntradas() : 0);
            row.createCell(7).setCellValue(m.getSalidas() != null ? m.getSalidas() : 0);
            row.createCell(8).setCellValue(m.getCategoria() != null ? m.getCategoria() : "");

            // Columna "Acciones" vacía
            row.createCell(9).setCellValue("");
        }

        // Ajustar ancho columnas de 0 a 9 (10 columnas)
        for (int i = 0; i <= 9; i++) {
            sheet.autoSizeColumn(i);
        }

        // Exportar a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        return baos.toByteArray();
    }

    private void crearTitulo(Workbook workbook, Sheet sheet) {
        Row tituloRow = sheet.createRow(0);
        Cell tituloCell = tituloRow.createCell(0);
        tituloCell.setCellValue("Reporte de Inventario de Materiales");

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);

        tituloCell.setCellStyle(style);

        // Merge A1:J1 (columnas 0 a 9)
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
    }

    private void crearFecha(Workbook workbook, Sheet sheet) {
        Row fechaRow = sheet.createRow(1);
        Cell fechaCell = fechaRow.createCell(0);

        ZoneId zona = ZoneId.of("America/Mexico_City");
        String fechaStr = "Fecha de reporte: " +
                ZonedDateTime.now(zona).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        fechaCell.setCellValue(fechaStr);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setItalic(true);
        font.setColor(IndexedColors.BLACK.getIndex());
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);

        fechaCell.setCellStyle(style);

        // Merge A2:J2 (columnas 0 a 9)
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 9));
    }

    private void crearEncabezados(Workbook workbook, Sheet sheet) {
        Row headerRow = sheet.createRow(3); // fila 4 (índice 3)
        String[] headers = {"ID", "Clave", "Descripción", "Unidad", "Cantidad", "Precio Unitario", "Entradas", "Salidas", "Categoría"};

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }
}






