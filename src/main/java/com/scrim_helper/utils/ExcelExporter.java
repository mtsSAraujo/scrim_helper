package com.scrim_helper.utils;

import com.scrim_helper.models.FinalDetails;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {
    public static void exportToExcel(List<FinalDetails> matchesDetails, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Matches");

        // Criando cabeçalho
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Placement", "Match End", "Hero", "Player", "Kills", "Deaths", "Assists", "Damage Done", "Damage Taken", "Heal Done"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(createHeaderStyle(workbook));
        }

        // Adicionando os dados das partidas
        int rowNum = 1;
        for (FinalDetails match : matchesDetails) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(match.getPlacement());
            row.createCell(1).setCellValue(match.getMatchEnd());
            row.createCell(2).setCellValue(match.getHero());
            row.createCell(3).setCellValue(match.getPlayer().getPlayerName());
            row.createCell(4).setCellValue(match.getStats().getKills());
            row.createCell(5).setCellValue(match.getStats().getDeaths());
            row.createCell(6).setCellValue(match.getStats().getAssists());
            row.createCell(7).setCellValue(match.getStats().getDamageDone());
            row.createCell(8).setCellValue(match.getStats().getDamageTaken());
            row.createCell(9).setCellValue(match.getStats().getHealingGiven());
        }

        // Ajustando o tamanho das colunas
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Escrevendo no arquivo
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            System.out.println("Planilha Excel criada com sucesso: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para criar estilo do cabeçalho
    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
}
