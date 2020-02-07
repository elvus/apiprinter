package com.printer.apiprinter.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterName;

import com.printer.apiprinter.conexion.conexion;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;

import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

public class printers {

    public static PrintService findPrintService(String printerName) {

        printerName = printerName.toLowerCase();

        PrintService service = null;

        // Get array of all print services
        PrintService[] services = PrinterJob.lookupPrintServices();

        // Retrieve a print service from the array
        for (int index = 0; service == null && index < services.length; index++) {

            if (services[index].getName().toLowerCase().contains(printerName)) {
                service = services[index];
            } else {
            }
        }

        // Return the print service
        return service;
    }

    /**
     * Retrieves a List of Printer Service Names.
     *
     * @return List
     */
    public static List<String> getPrinterServiceNameList() {

        // get list of all print services
        PrintService[] services = PrinterJob.lookupPrintServices();
        List<String> list = new ArrayList<>();

        for (PrintService service : services) {
            list.add(service.getName());
        }
        return list;
    }

    public static void SetReport(Map<String, Object> map) {
        try {
            InputStream input = new FileInputStream(
                    System.getProperty("user.dir") + File.separator + "config.properties");
            Properties prop = new Properties();
            prop.load(input);

            File reporte = new File((String) System.getProperty("user.dir") + File.separator
                    + prop.getProperty("reportFolder") + File.separator + map.get("report"));

            Connection con = conexion.getConnection(String.format("jdbc:mysql://%s:%s/%s", prop.getProperty("url"),
                    prop.getProperty("port"), prop.getProperty("database")), prop.getProperty("user"),
                    prop.getProperty("pass"));

            map.put("SUBREPORT_DIR", System.getProperty("user.dir") + File.separator + prop.getProperty("reportFolder")
                    + File.separator + map.get("subreport"));
            map.put("IMAGE_DIR", System.getProperty("user.dir") + File.separator + prop.getProperty("imageFolder")
                    + File.separator + map.get("image"));

            JasperReport rep = (JasperReport) JRLoader.loadObject(reporte);
            JasperPrint jasperPrint = JasperFillManager.fillReport(rep, map, con);
            PrintReportToPrinter(jasperPrint, (String) map.get("printer"));

        } catch (IOException | JRException ex) {
            ex.printStackTrace();
        }
    }

    public static void PrintReportToPrinter(JasperPrint jp, String impresora) throws JRException {
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(OrientationRequested.PORTRAIT);
        printRequestAttributeSet.add(new Copies(1));
        PrinterName printerName = new PrinterName(impresora, null); // get printer

        PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
        printServiceAttributeSet.add(printerName);

        JRPrintServiceExporter exporter = new JRPrintServiceExporter();

        exporter.setExporterInput(new SimpleExporterInput(jp));
        SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
        configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
        configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
        configuration.setDisplayPageDialog(false);
        configuration.setDisplayPrintDialog(false);
        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }
}