/**
 * 
 *	哈尔滨银行
 * Copyright (c) 2007-2015 HRBB,Inc.All Rights Reserved.
 */
package com.hrbb.loan.pos.biz.backstage.inter.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.common.io.Files;
import com.hrbb.loan.pos.biz.backstage.inter.SynFileSummaryBiz;
import com.hrbb.loan.pos.biz.backstage.inter.SynFileUploadBiz;
import com.hrbb.loan.pos.biz.constants.SynFileConstants;
import com.hrbb.loan.pos.service.BusinessDictionaryService;
import com.hrbb.loan.pos.service.CreditApplyForReviewService;
import com.hrbb.loan.pos.service.ReceiptInfoService;
import com.hrbb.loan.pos.util.FileUtil;
import com.hrbb.loan.pos.util.StringUtil;
import com.hrbb.loan.pos.util.constants.ReviewNoteConstants;

/**
 * 数据同步接口
 * 
 * @author marco
 * @version $Id: LedgerBizImpl.java, v 0.1 2015-4-24 下午4:20:22 marco Exp $
 */
@Component("synFileSummaryBiz")
public class SynFileSummaryBizImpl implements SynFileSummaryBiz {

	private Logger LOG = LoggerFactory.getLogger(SynFileSummaryBizImpl.class);

	@Autowired
	@Qualifier("creditApplyForReviewService")
	private CreditApplyForReviewService serviceCAFR;

	@Autowired
	@Qualifier("businessDictionaryService")
	private BusinessDictionaryService businessDictionaryService;

	@Autowired
	@Qualifier("receiptInfoService")
	private ReceiptInfoService serviceRI;

	@Autowired
	@Qualifier("synFileUploadBiz")
	private SynFileUploadBiz bizUpload;

	/**
	 * 获取项目路径
	 * 
	 * @return
	 */
	private String getSynFilePath() {
		// 项目路径
		String path = "";
		try {
			path = this.getClass().getClassLoader().getResource("../../")
					.toURI().getPath()
					+ SynFileConstants.SYNFILESUMMARY;
		} catch (URISyntaxException e) {
			LOG.error("获取输出路径失败！", e);
			return null;
		}
		// linux下不能去掉前面的斜线（要注释），windows下要去掉（放开注释）
		// path = path.substring(1);
		return path;
	}

	/**
	 * @see com.hrbb.loan.pos.biz.backstage.inter.LedgerBiz#makeSynFile()
	 */
	@Override
	public Map<String, String> upload(String fileName) {

		LOG.info("SynFileSummaryBizImpl.upload begin");
		Map<String, String> resp = new HashMap<>();
		// 项目路径
		String path = getSynFilePath();
		if (StringUtil.isEmpty(path)) {
			resp.put("code", "9");
			resp.put("msg", "获取输出路径失败！");
			return resp;
		}

		// 取得路径
		LOG.debug("文件路径path=" + path);
		LOG.debug("文件名fileName=" + fileName);

		// 判断文件是否存在
		if (FileUtil.checkFileExist(path + fileName)) {
			LOG.debug("文件存在，删除汇总以外sheet。");
			String fileNameTmp = "Tmp" + fileName;
			LOG.debug("发给银商临时文件名fileNameTmp=" + fileNameTmp);
			// 发给银商文件名
			String fileNameUM = fileName.replaceFirst(
					SynFileConstants.NO_SUMMARY_ALL, "");
			LOG.debug("发给银商文件名fileNameUM=" + fileNameUM);
			String allPathUM = path + fileNameUM;

			File file = new File(path + fileName);
			File fileTemp = new File(path + fileNameTmp);
			try {
				Files.copy(file, fileTemp);
				LOG.debug("创建临时文件成功。");
			} catch (IOException e2) {
				LOG.error("创建临时文件失败:", e2);
				resp.put("code", "9");
				resp.put("msg", "创建临时文件失败");
				return resp;
			}
			try {
				Workbook workbook = new XSSFWorkbook(path + fileNameTmp);
				// 删除除汇总之外的sheet
				workbook.removeSheetAt(1);
				workbook.removeSheetAt(1);
				workbook.removeSheetAt(1);
				workbook.removeSheetAt(1);
				workbook.removeSheetAt(1);
				LOG.debug("删除除汇总之外的sheet成功。");
				// 保存文件
				if (!saveFile(allPathUM, workbook)) {
					LOG.error("保存文件失败！");
					resp.put("code", "9");
					resp.put("msg", "保存文件失败");
					return resp;
				}
			} catch (IOException e1) {
				LOG.error("Error on CREATING excel workbook:", e1);
				resp.put("code", "9");
				resp.put("msg", "上传失败，请联系系统管理员。");
				return resp;
			}

			String msg = "";
			try {
				msg = bizUpload.upload(allPathUM);
				LOG.debug("msg=" + msg);
			} catch (Exception e) {
				resp.put("code", "9");
				resp.put("msg", "上传失败，请联系系统管理员。");
				return resp;
			}
			// 删除临时文件
			if (fileTemp != null && fileTemp.exists()) {
				fileTemp.delete();
			}
			FileUtil.deleteIfExists(allPathUM);
			if (msg.indexOf("成功") == -1) {
				resp.put("code", "9");
				resp.put("msg", msg);
			} else {
				resp.put("code", "0");
				resp.put("msg", msg);
			}
			LOG.debug("删除临时文件成功");
			LOG.info("SynFileSummaryBizImpl end");
			return resp;
		} else {
			resp.put("code", "9");
			resp.put("msg", "文件不存在！");
			LOG.info("SynFileSummaryBizImpl end");
			return resp;
		}
	}

	/**
	 * @see com.hrbb.loan.pos.biz.backstage.inter.LedgerBiz#makeSynFile()
	 */
	@Override
	public Map<String, String> makeSynFile(String channel, String fromDay,
			String toDay) {

		LOG.debug("makeSynFile begin");

		Map<String, String> resp = new HashMap<>();

		// 项目路径
		String path = getSynFilePath();
		if (StringUtil.isEmpty(path)) {
			resp.put("code", "9");
			resp.put("msg", "获取输出路径失败！");
			return resp;
		}

		// 取得路径
		LOG.debug("取得路径pathStr=" + path);
		// 判断路径存在
		if (!FileUtil.createDirectory(path)) {
			LOG.error("创建路径失败！");
			resp.put("code", "9");
			resp.put("msg", "创建路径失败！");
			return resp;
		} else {
			LOG.debug("路径存在");
		}

		LOG.debug("channel=" + channel);
		LOG.debug("fromDay=" + fromDay);
		LOG.debug("toDay=" + toDay);

		// 文件名
		String fileName = SynFileConstants.NO_SUMMARY_ALL
				+ SynFileConstants.NO_SUMMARY;

		if (StringUtil.isNotEmpty(channel)) {
			fileName = fileName.concat(SynFileConstants.STRING_UNDERLINE)
					.concat(channel);
		}
		if (StringUtil.isNotEmpty(fromDay)) {
			fileName = fileName.concat(SynFileConstants.STRING_UNDERLINE)
					.concat("from" + fromDay.replaceAll("-", ""));
		}
		if (StringUtil.isNotEmpty(toDay)) {
			fileName = fileName.concat(SynFileConstants.STRING_UNDERLINE)
					.concat("to" + toDay.replaceAll("-", ""));
		}
		fileName = fileName.concat(FileUtil.FILETYPE_XLSX);

		// 判断文件是否存在，存在测删除
		String allPath = path + fileName;
		LOG.debug("allPath=" + allPath);
		if (FileUtil.checkFileExist(allPath)) {
			LOG.debug("文件存在，删除");
			FileUtil.deleteIfExists(allPath);
		} else {
			LOG.debug("文件不存在。");
		}

		LOG.debug("创建文件fileName=" + fileName);

		// 创建文件
		Workbook workbook = makeFileTemplete();
		if (workbook == null) {
			LOG.error("创建文件失败！");
			resp.put("code", "9");
			resp.put("msg", "创建文件失败！");
			return resp;
		}

		// 写入数据
		setRowData(workbook, channel, fromDay, toDay);

		// 保存文件
		if (!saveFile(allPath, workbook)) {
			LOG.error("保存文件失败！");
			resp.put("code", "9");
			resp.put("msg", "保存文件失败！");
			return resp;
		}
		LOG.debug("保存文件成功");
		resp.put("code", "0");
		resp.put("msg", "生成报表成功。\n文件名为：" + fileName);
		resp.put("fileName", fileName);
		LOG.debug("makeSynFile end");
		return resp;
	}

	/**
	 * 银商数据统计表取得数据，生成文件
	 * 
	 * @return boolean
	 */
	private Workbook makeFileTemplete() {

		Workbook workbook = null;
		try {
			// 创建文件
			// XSSFWork used for .xslx (>= 2007), HSSWorkbook for 03 .xsl
			// workbook = new HSSFWorkbook();
			workbook = new XSSFWorkbook();
		} catch (Exception e) {
			LOG.error("Error on CREATING excel workbook:", e);
			return null;
		}
		LOG.debug("创建文件成功");

		// 创建标题
		createTitleRow(workbook);

		LOG.debug("创建文件模板成功");
		return workbook;
	}

	/**
	 * 保存文件-模板
	 * 
	 * @param pathStr
	 * @return
	 */
	private boolean saveFile(String pathStr, Workbook workbook) {

		LOG.debug("保存文件全路径pathStr=" + pathStr);

		// 写入文件流，创建文件
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(pathStr);
			workbook.write(outputStream);
			outputStream.flush();
			LOG.debug("保存文件成功。");
		} catch (Exception e) {
			LOG.error("Error on CREATING excel workbook:", e);
			return false;
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					// 关闭失败，不影响业务
					LOG.debug("outputStream Error on saving excel workbook:", e);
				}
			}
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					// 关闭失败，不影响业务
					LOG.debug("workbook Error on saving excel workbook:", e);
				}
			}
		}
		return true;
	}

	/**
	 * 创建数据行
	 * 
	 * @param sheet
	 * @param style
	 * @return
	 */
	private void setRowData(Workbook workbook, String channel, String fromDay,
			String toDay) {

		LOG.debug("写入数据开始");
		// 创建式样
		CellStyle style = getStyleCell(workbook);

		Sheet sheet = null;
		Row row = null;
		Cell cell = null;

		Map<String, String> p = new HashMap<String, String>();
		if (StringUtil.isNotEmpty(channel)) {
			p.put("channel", channel);
		}
		if (StringUtil.isNotEmpty(fromDay)) {
			p.put("fromDay", fromDay);
		}
		if (StringUtil.isNotEmpty(toDay)) {
			p.put("toDay", toDay);
		}

		// 等额本息存量客户名单
		List<Map<String, Object>> data1 = getData(p, 1);
		int rowCount1 = data1.size();
		LOG.debug("rowCount1=" + rowCount1);
		if (rowCount1 > 0) {
			Map<String, Object> map = null;
			// 取得sheet
			sheet = workbook.getSheet(SynFileConstants.SHEET_NAME_SUMMARY_1);

			for (int i = 0; i < rowCount1; i++) {

				row = sheet.createRow(i + 1);
				map = data1.get(i);

				for (int j = 0; j < 8; j++) {

					cell = row.createCell(j);

					switch (j) {
					case 0:
						if (map.get("loanid") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("loanid").toString());
						}
						break;
					case 1:
						if (map.get("indate") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("indate").toString());
						}
						break;
					case 2:
						if (map.get("channel") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("channel").toString());
						}
						break;
					case 3:
						if (map.get("prodname") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("prodname").toString());
						}
						break;
					case 4:
						if (map.get("custname") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("custname").toString());
						}
						break;
					case 5:
						if (map.get("paperId") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("paperId").toString());
						}
						break;
					case 6:
						if (map.get("poscustname") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("poscustname").toString());
						}
						break;
					case 7:
						if (map.get("AccrualType") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("AccrualType").toString());
						}
						break;
					default:
						cell.setCellValue("");
						break;
					}
					cell.setCellStyle(style);
				}
			}
		}

		// 模型审批
		List<Map<String, Object>> data2 = getData(p, 2);
		int rowCount2 = data2.size();
		LOG.debug("rowCount2=" + rowCount2);
		if (rowCount2 > 0) {
			Map<String, Object> map = null;
			// 取得sheet
			sheet = workbook.getSheet(SynFileConstants.SHEET_NAME_SUMMARY_2);

			for (int i = 0; i < rowCount2; i++) {

				row = sheet.createRow(i + 1);
				map = data2.get(i);

				for (int j = 0; j < 16; j++) {

					cell = row.createCell(j);

					switch (j) {
					case 0:
						if (map.get("loanid") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("loanid").toString());
						}
						break;
					case 1:
						if (map.get("indate") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("indate").toString());
						}
						break;
					case 2:
						if (map.get("channel") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("channel").toString());
						}
						break;
					case 3:
						if (map.get("priv") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("priv").toString());
						}
						break;
					case 4:
						if (map.get("city") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("city").toString());
						}
						break;
					case 5:
						if (map.get("prodname") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("prodname").toString());
						}
						break;
					case 6:
						if (map.get("poscustname") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("poscustname").toString());
						}
						break;
					case 7:
						if (map.get("custname") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("custname").toString());
						}
						break;
					case 8:
						if (map.get("paperId") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("paperId").toString());
						}
						break;
					case 9:
						if (map.get("mobilephone") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("mobilephone").toString());
						}
						break;
					case 10:
						if (map.get("posCustAddress") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("posCustAddress")
									.toString());
						}
						break;
					case 11:
						if (map.get("apprResult") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("apprResult").toString());
						}
						break;
					case 12:
						if (map.get("appEndTime") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("appEndTime").toString());
						}
						break;
					case 13:
						if (map.get("apprAmount") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("apprAmount").toString());
						}
						break;
					case 14:
						if (map.get("apprInte") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("apprInte").toString());
						}
						break;
					case 15:
						if (map.get("apprInfoExt") == null) {
							cell.setCellValue("");
						} else {
							String apprInfoExtMsg = businessDictionaryService
									.getApprInfoExtMsg(map.get("apprInfoExt")
											.toString());
							cell.setCellValue(apprInfoExtMsg);
						}
						break;
					default:
						cell.setCellValue("");
						break;
					}
					cell.setCellStyle(style);
				}
			}
		}

		// 复审审批
		List<Map<String, Object>> data3 = getData(p, 3);
		int rowCount3 = data3.size();
		LOG.debug("rowCount3=" + rowCount3);
		if (rowCount3 > 0) {
			Map<String, Object> map = null;
			// 取得sheet
			sheet = workbook.getSheet(SynFileConstants.SHEET_NAME_SUMMARY_3);

			for (int i = 0; i < rowCount3; i++) {

				row = sheet.createRow(i + 1);
				map = data3.get(i);

				for (int j = 0; j < 18; j++) {

					cell = row.createCell(j);

					switch (j) {
					case 0:
						if (map.get("loanid") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("loanid").toString());
						}
						break;
					case 1:
						if (map.get("indate") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("indate").toString());
						}
						break;
					case 2:
						if (map.get("channel") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("channel").toString());
						}
						break;
					case 3:
						if (map.get("priv") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("priv").toString());
						}
						break;
					case 4:
						if (map.get("city") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("city").toString());
						}
						break;
					case 5:
						if (map.get("prodname") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("prodname").toString());
						}
						break;
					case 6:
						if (map.get("poscustname") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("poscustname").toString());
						}
						break;
					case 7:
						if (map.get("custname") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("custname").toString());
						}
						break;
					case 8:
						if (map.get("paperId") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("paperId").toString());
						}
						break;
					case 9:
						if (map.get("mobilephone") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("mobilephone").toString());
						}
						break;
					case 10:
						if (map.get("posCustAddress") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("posCustAddress")
									.toString());
						}
						break;
					case 11:
						if (map.get("apprResult") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("apprResult").toString());
						}
						break;
					case 12:
						if (map.get("apprAmount") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("apprAmount").toString());
						}
						break;
					case 13:
						if (map.get("apprInte") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("apprInte").toString());
						}
						break;
					case 14:
						if (map.get("appEndTime") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("appEndTime").toString());
						}
						break;
					case 15:
						if (map.get("AccrualType") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("AccrualType").toString());
						}
						break;
					case 16:
						if (map.get("apprInfoExt") == null) {
							cell.setCellValue("");
						} else {
							String apprInfoExtMsg = businessDictionaryService
									.getApprInfoExtMsg(map.get("apprInfoExt")
											.toString());
							cell.setCellValue(apprInfoExtMsg);
						}
						break;
					case 17:
						if (map.get("OccurType") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("OccurType").toString());
						}
						break;
					default:
						cell.setCellValue("");
						break;
					}
					cell.setCellStyle(style);
				}
			}
		}

		// 合同信息
		List<Map<String, Object>> data4 = getData(p, 4);
		int rowCount4 = data4.size();
		LOG.debug("rowCount4=" + rowCount4);
		if (rowCount4 > 0) {
			Map<String, Object> map = null;
			// 取得sheet
			sheet = workbook.getSheet(SynFileConstants.SHEET_NAME_SUMMARY_4);

			for (int i = 0; i < rowCount4; i++) {

				row = sheet.createRow(i + 1);
				map = data4.get(i);

				for (int j = 0; j < 18; j++) {

					cell = row.createCell(j);

					switch (j) {
					case 0:
						if (map.get("loanid") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("loanid").toString());
						}
						break;
					case 1:
						if (map.get("channel") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("channel").toString());
						}
						break;
					case 2:
						if (map.get("contno") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("contno").toString());
						}
						break;
					case 3:
						if (map.get("custname") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("custname").toString());
						}
						break;
					case 4:
						if (map.get("paperid") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("paperid").toString());
						}
						break;
					case 5:
						if (map.get("prodname") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("prodname").toString());
						}
						break;
					case 6:
						if (map.get("currSign") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("currSign").toString());
						}
						break;
					case 7:
						if (map.get("paybackMethod") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("paybackMethod")
									.toString());
						}
						break;
					case 8:
						if (map.get("creditline") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("creditline").toString());
						}
						break;
					case 9:
						if (map.get("creditinterest") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("creditinterest")
									.toString());
						}
						break;
					case 10:
						if (map.get("termUnit") == null) {
							cell.setCellValue("");
						} else {
							switch (map.get("termUnit").toString()) {
							case "Y":
								cell.setCellValue("年");
								break;
							case "M":
								cell.setCellValue("月");
								break;
							case "D":
								cell.setCellValue("日");
								break;
							default:
								cell.setCellValue("");
								break;
							}
						}
						break;
					case 11:
						if (map.get("contterm") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("contterm").toString());
						}
						break;
					case 12:
						if (map.get("begindate") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("begindate").toString());
						}
						break;
					case 13:
						if (map.get("enddate") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("enddate").toString());
						}
						break;
					case 14:
						if (map.get("agreementStatus") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("agreementStatus")
									.toString());
						}
						break;
					case 15:
						if (map.get("begindate") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("begindate").toString());
						}
						break;
					case 16:
						if (map.get("operId") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("operId").toString());
						}
						break;
					case 17:
						if (map.get("OccurType") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("OccurType").toString());
						}
						break;
					default:
						cell.setCellValue("");
						break;
					}
					cell.setCellStyle(style);
				}
			}
		}

		// 放款台账
		List<Map<String, Object>> data5 = getData(p, 5);
		int rowCount5 = data5.size();
		LOG.debug("rowCount5=" + rowCount5);
		if (rowCount5 > 0) {
			Map<String, Object> map = null;
			// 取得sheet
			sheet = workbook.getSheet(SynFileConstants.SHEET_NAME_SUMMARY_5);

			for (int i = 0; i < rowCount5; i++) {

				row = sheet.createRow(i + 1);
				map = data5.get(i);

				for (int j = 0; j < 19; j++) {

					cell = row.createCell(j);

					switch (j) {
					case 0:
						if (map.get("contno") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("contno").toString());
						}
						break;
					case 1:
						if (map.get("receiptid") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("receiptid").toString());
						}
						break;
					case 2:
						if (map.get("custname") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("custname").toString());
						}
						break;
					case 3:
						if (map.get("paperid") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("paperid").toString());
						}
						break;
					case 4:
						if (map.get("channel") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("channel").toString());
						}
						break;
					case 5:
						if (map.get("prodname") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("prodname").toString());
						}
						break;
					case 6:
						if (map.get("priv") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("priv").toString());
						}
						break;
					case 7:
						if (map.get("city") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("city").toString());
						}
						break;
					case 8:
						if (map.get("creditline") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("creditline").toString());
						}
						break;
					case 9:
						if (map.get("currSign") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("currSign").toString());
						}
						break;
					case 10:
						if (map.get("AccrualType") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("AccrualType").toString());
						}
						break;
					case 11:
						if (map.get("payapplyamt") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("payapplyamt").toString());
						}
						break;
					case 12:
						if (map.get("loantotalbalance") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("loantotalbalance")
									.toString());
						}
						break;
					case 13:
						if (map.get("loaninterest") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("loaninterest")
									.toString());
						}
						break;
					case 14:
						if (map.get("actualissuedate") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("actualissuedate")
									.toString());
						}
						break;
					case 15:
						if (map.get("actualmaturity") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("actualmaturity")
									.toString());
						}
						break;
					case 16:
						if (map.get("finishdate") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("finishdate").toString());
						}
						break;
					case 17:
						if (map.get("LoanStatus") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("LoanStatus").toString());
						}
						break;
					case 18:
						if (map.get("OccurType") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("OccurType").toString());
						}
						break;
					default:
						cell.setCellValue("");
						break;
					}
					cell.setCellStyle(style);
				}
			}
		}

		// 汇总-基本信息
		List<Map<String, Object>> dataBase = getData(p, 9);
		int rowCountBase = dataBase.size();
		LOG.debug("rowCountBase=" + rowCountBase);
		if (rowCountBase > 0) {
			Map<String, Object> map = null;
			// 取得sheet
			sheet = workbook.getSheet(SynFileConstants.SHEET_NAME_SUMMARY_0);
			// 初审数据
			List<Map<String, Object>> data0 = getData(p, 0);
			int rowCount0 = data0.size();
			LOG.debug("rowCount0=" + rowCount0);

			// 放款数据
			data5 = serviceRI.selectSummaryForCont(null);
			rowCount5 = data5.size();
			LOG.debug("汇总rowCount5=" + rowCount5);

			// 初审数据
			Map<String, Object> map0 = null;
			// 复审数据
			Map<String, Object> map3 = null;
			// 合同数据
			Map<String, Object> map4 = null;
			// 放款数据
			Map<String, Object> map5 = null;
			// 汇总数据循环
			for (int i = 0; i < rowCountBase; i++) {

				row = sheet.createRow(i + 1);

				map = dataBase.get(i);
				// 查找对应的初审数据
				map0 = new HashMap<>();
				for (int j = 0; j < rowCount0; j++) {
					if (map.get("loanid").equals(data0.get(j).get("loanid"))) {
						map0 = data0.get(j);
						break;
					}
				}
				// 查找对应的复审数据
				map3 = new HashMap<>();
				for (int j = 0; j < rowCount3; j++) {
					if (map.get("loanid").equals(data3.get(j).get("loanid"))) {
						map3 = data3.get(j);
						break;
					}
				}
				// 查找对应的合同数据
				map4 = new HashMap<>();
				for (int j = 0; j < rowCount4; j++) {
					if (map.get("loanid").equals(data4.get(j).get("loanid"))) {
						map4 = data4.get(j);
						break;
					}
				}
				// 查找对应的放款数据
				map5 = new HashMap<>();
				if (map4.get("contno") != null) {
					for (int j = 0; j < rowCount5; j++) {
						if (map4.get("contno").equals(
								data5.get(j).get("contno"))) {
							map5 = data5.get(j);
							break;
						}
					}
				}
				// 汇总数据各列
				for (int j = 0; j < 23; j++) {

					cell = row.createCell(j);

					switch (j) {
					case 0:
						if (map.get("loanid") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("loanid").toString());
						}
						break;
					case 1:
						if (map.get("indate") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("indate").toString());
						}
						break;
					case 2:
						if (map.get("channel") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("channel").toString());
						}
						break;
					case 3:
						if (map.get("stdmerno") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("stdmerno").toString());
						}
						break;
					case 4:
						if (map.get("prodname") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("prodname").toString());
						}
						break;
					case 5:
						if (map.get("custname") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("custname").toString());
						}
						break;
					case 6:
						if (map.get("paperId") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("paperId").toString());
						}
						break;
					case 7:
						if (map.get("priv") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("priv").toString());
						}
						break;
					case 8:
						if (map.get("city") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map.get("city").toString());
						}
						break;
					// 初审数据
					case 9:
						if (map0.get("apprResultName") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map0.get("apprResultName")
									.toString());
						}
						break;
					case 10:
						// 没有审批结果时
						if (map0.get("apprresult") == null) {
							cell.setCellValue("");
						} else {
							if (map0.get("needReason") == null) {
								cell.setCellValue("");
							} else {
								// 补件时
								if (ReviewNoteConstants.APPRRESULT_CODE_31
										.equals(map0.get("apprresult")
												.toString())) {
									cell.setCellValue(map0.get("needReason")
											.toString());
								} else {
									String apprInfoExtMsg = businessDictionaryService
											.getApprInfoExtMsg(map0.get(
													"needReason").toString());
									cell.setCellValue(apprInfoExtMsg);
								}
							}
						}
						break;
					case 11:
						if (map0.get("appBeginTime") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map0.get("appBeginTime")
									.toString());
						}
						break;
					case 12:
						if (map0.get("appEndTime") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map0.get("appEndTime").toString());
						}
						break;
					// 初审备注
					case 13:
						if (map0.get("remark") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map0.get("remark").toString());
						}
						break;
					// 复审数据
					case 14:
						if (map3.get("appEndTime") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map3.get("appEndTime").toString());
						}
						break;
					case 15:
						if (map3.get("apprResult") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map3.get("apprResult").toString());
						}
						break;
					case 16:
						if (map3.get("apprAmount") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map3.get("apprAmount").toString());
						}
						break;
					case 17:
						if (map3.get("apprInte") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map3.get("apprInte").toString());
						}
						break;
					case 18:
						if (map3.get("apprInfoExt") == null) {
							cell.setCellValue("");
						} else {
							String apprInfoExtMsg = businessDictionaryService
									.getApprInfoExtMsg(map3.get("apprInfoExt")
											.toString());
							cell.setCellValue(apprInfoExtMsg);
						}
						break;
					// 合同数据
					case 19:
						if (map4.get("agreementStatus") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map4.get("agreementStatus")
									.toString());
						}
						break;
					case 20:
						if (map4.get("contno") == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(map4.get("contno").toString());
						}
						break;
					// 放款数据
					case 21:
						if (map5.get("cnt") == null) {
							cell.setCellValue("0");
						} else {
							cell.setCellValue(map5.get("cnt").toString());
						}
						break;
					case 22:
						if (map5.get("payApplyAmtSum") == null) {
							cell.setCellValue("0.00");
						} else {
							cell.setCellValue(map5.get("payApplyAmtSum")
									.toString());
						}
						break;
					default:
						cell.setCellValue("");
						break;
					}
					cell.setCellStyle(style);
				}
			}
		}
		LOG.debug("写入数据结束");
	}

	/**
	 * 查询数据
	 * 
	 * @return
	 */
	private List<Map<String, Object>> getData(Map<String, String> p, int flag) {

		List<Map<String, Object>> summaryData = null;
		switch (flag) {
		case 0:
			summaryData = serviceCAFR.selectSummary0(p);
			break;
		case 1:
			summaryData = serviceCAFR.selectSummary1(p);
			break;
		case 2:
			summaryData = serviceCAFR.selectSummary2(p);
			break;
		case 3:
			summaryData = serviceCAFR.selectSummary3(p);
			break;
		case 4:
			summaryData = serviceCAFR.selectSummary4(p);
			break;
		case 5:
			summaryData = serviceCAFR.selectSummary5(p);
			break;
		default:
			summaryData = serviceCAFR.selectSummaryBase(p);
			break;
		}
		return summaryData;
	}

	/**
	 * 创建模板
	 * 
	 * @param sheet
	 * @param style
	 * @return
	 */
	private void createTitleRow(Workbook workbook) {

		// 创建1个sheet
		Sheet sheet = workbook
				.createSheet(SynFileConstants.SHEET_NAME_SUMMARY_0);

		// 创建标题
		CellStyle styleTitle = getStyleTitle(workbook);

		Row row0 = sheet.createRow(0);

		for (int i = 0; i < 23; i++) {
			Cell cell = row0.createCell(i);
			switch (i) {
			case 0:
				cell.setCellValue("申请编号");
				break;
			case 1:
				cell.setCellValue("进件日期");
				break;
			case 2:
				cell.setCellValue("渠道名称");
				break;
			case 3:
				cell.setCellValue("商户编号");
				break;
			case 4:
				cell.setCellValue("贷款产品名");
				break;
			case 5:
				cell.setCellValue("客户姓名");
				break;
			case 6:
				cell.setCellValue("身份证号");
				break;
			case 7:
				cell.setCellValue("商户归属省");
				break;
			case 8:
				cell.setCellValue("商户归属市");
				break;
			case 9:
				cell.setCellValue("初审是否通过");
				break;
			case 10:
				cell.setCellValue("未通过原因");
				break;
			case 11:
				cell.setCellValue("初审时间");
				break;
			case 12:
				cell.setCellValue("审件时间");
				break;
			case 13:
				cell.setCellValue("初审备注");
				break;
			case 14:
				cell.setCellValue("审批时间");
				break;
			case 15:
				cell.setCellValue("终审结果");
				break;
			case 16:
				cell.setCellValue("额度(元)");
				break;
			case 17:
				cell.setCellValue("利率(%)");
				break;
			case 18:
				cell.setCellValue("对外拒绝原因");
				break;
			case 19:
				cell.setCellValue("合同状态");
				break;
			case 20:
				cell.setCellValue("合同编号");
				break;
			case 21:
				cell.setCellValue("用款次数");
				break;
			case 22:
				cell.setCellValue("累计用款金额（元）");
				break;
			default:
				break;
			}
			cell.setCellStyle(styleTitle);
			sheet.autoSizeColumn(i);
		}

		// 创建1个sheet
		sheet = workbook.createSheet(SynFileConstants.SHEET_NAME_SUMMARY_1);

		row0 = sheet.createRow(0);

		for (int i = 0; i < 8; i++) {
			Cell cell = row0.createCell(i);
			switch (i) {
			case 0:
				cell.setCellValue("申请编号");
				break;
			case 1:
				cell.setCellValue("申请日期");
				break;
			case 2:
				cell.setCellValue("渠道名称");
				break;
			case 3:
				cell.setCellValue("贷款产品名");
				break;
			case 4:
				cell.setCellValue("客户名");
				break;
			case 5:
				cell.setCellValue("身份证号");
				break;
			case 6:
				cell.setCellValue("商户名称");
				break;
			case 7:
				cell.setCellValue("还款方式");
				break;
			default:
				break;
			}
			cell.setCellStyle(styleTitle);
			sheet.autoSizeColumn(i);
		}

		// 创建1个sheet
		sheet = workbook.createSheet(SynFileConstants.SHEET_NAME_SUMMARY_2);

		row0 = sheet.createRow(0);

		for (int i = 0; i < 16; i++) {
			Cell cell = row0.createCell(i);
			switch (i) {
			case 0:
				cell.setCellValue("申请编号");
				break;
			case 1:
				cell.setCellValue("申请日期");
				break;
			case 2:
				cell.setCellValue("渠道名称");
				break;
			case 3:
				cell.setCellValue("归属省");
				break;
			case 4:
				cell.setCellValue("归属市");
				break;
			case 5:
				cell.setCellValue("产品名称");
				break;
			case 6:
				cell.setCellValue("商户名称");
				break;
			case 7:
				cell.setCellValue("申请人姓名");
				break;
			case 8:
				cell.setCellValue("身份证号");
				break;
			case 9:
				cell.setCellValue("申请人手机");
				break;
			case 10:
				cell.setCellValue("经营地址");
				break;
			case 11:
				cell.setCellValue("模型审批结果");
				break;
			case 12:
				cell.setCellValue("模型审批时间");
				break;
			case 13:
				cell.setCellValue("模型审批金额(元)");
				break;
			case 14:
				cell.setCellValue("模型审批利率(%)");
				break;
			case 15:
				cell.setCellValue("模型对外拒绝理由");
				break;
			default:
				break;
			}
			cell.setCellStyle(styleTitle);
			sheet.autoSizeColumn(i);
		}

		// 创建1个sheet
		sheet = workbook.createSheet(SynFileConstants.SHEET_NAME_SUMMARY_3);

		row0 = sheet.createRow(0);

		for (int i = 0; i < 18; i++) {
			Cell cell = row0.createCell(i);
			switch (i) {
			case 0:
				cell.setCellValue("申请编号");
				break;
			case 1:
				cell.setCellValue("申请日期");
				break;
			case 2:
				cell.setCellValue("渠道名称");
				break;
			case 3:
				cell.setCellValue("归属省");
				break;
			case 4:
				cell.setCellValue("归属市");
				break;
			case 5:
				cell.setCellValue("产品名称");
				break;
			case 6:
				cell.setCellValue("商户名称");
				break;
			case 7:
				cell.setCellValue("申请人姓名");
				break;
			case 8:
				cell.setCellValue("身份证号");
				break;
			case 9:
				cell.setCellValue("申请人手机");
				break;
			case 10:
				cell.setCellValue("经营地址");
				break;
			case 11:
				cell.setCellValue("复审结果");
				break;
			case 12:
				cell.setCellValue("额度(元)");
				break;
			case 13:
				cell.setCellValue("利率（%）");
				break;
			case 14:
				cell.setCellValue("复审时间");
				break;
			case 15:
				cell.setCellValue("还款方式");
				break;
			case 16:
				cell.setCellValue("对外拒绝理由");
				break;
			case 17:
				cell.setCellValue("发生方式");
				break;
			default:
				break;
			}
			cell.setCellStyle(styleTitle);
			sheet.autoSizeColumn(i);
		}

		// 创建1个sheet
		sheet = workbook.createSheet(SynFileConstants.SHEET_NAME_SUMMARY_4);

		row0 = sheet.createRow(0);

		for (int i = 0; i < 18; i++) {
			Cell cell = row0.createCell(i);
			switch (i) {
			case 0:
				cell.setCellValue("申请编号");
				break;
			case 1:
				cell.setCellValue("渠道名称");
				break;
			case 2:
				cell.setCellValue("合同编号");
				break;
			case 3:
				cell.setCellValue("客户名称");
				break;
			case 4:
				cell.setCellValue("身份证号");
				break;
			case 5:
				cell.setCellValue("产品名称");
				break;
			case 6:
				cell.setCellValue("币种");
				break;
			case 7:
				cell.setCellValue("还款方式");
				break;
			case 8:
				cell.setCellValue("合同金额 (元)");
				break;
			case 9:
				cell.setCellValue("合同利率（%）");
				break;
			case 10:
				cell.setCellValue("期限类型");
				break;
			case 11:
				cell.setCellValue("合同期限");
				break;
			case 12:
				cell.setCellValue("合同起始日期");
				break;
			case 13:
				cell.setCellValue("合同到期日期");
				break;
			case 14:
				cell.setCellValue("合同状态");
				break;
			case 15:
				cell.setCellValue("合同生效日期");
				break;
			case 16:
				cell.setCellValue("经办人");
				break;
			case 17:
				cell.setCellValue("发生方式");
				break;
			default:
				break;
			}
			cell.setCellStyle(styleTitle);
			sheet.autoSizeColumn(i);
		}

		// 创建1个sheet
		sheet = workbook.createSheet(SynFileConstants.SHEET_NAME_SUMMARY_5);

		row0 = sheet.createRow(0);

		for (int i = 0; i < 19; i++) {
			Cell cell = row0.createCell(i);
			switch (i) {
			case 0:
				cell.setCellValue("合同编号");
				break;
			case 1:
				cell.setCellValue("借据编号");
				break;
			case 2:
				cell.setCellValue("客户名称");
				break;
			case 3:
				cell.setCellValue("身份证号");
				break;
			case 4:
				cell.setCellValue("渠道名称");
				break;
			case 5:
				cell.setCellValue("产品名称");
				break;
			case 6:
				cell.setCellValue("归属省");
				break;
			case 7:
				cell.setCellValue("归属市");
				break;
			case 8:
				cell.setCellValue("合同额度（元）");
				break;
			case 9:
				cell.setCellValue("币种");
				break;
			case 10:
				cell.setCellValue("还款方式");
				break;
			case 11:
				cell.setCellValue("放款金额（元）");
				break;
			case 12:
				cell.setCellValue("贷款余额（元）");
				break;
			case 13:
				cell.setCellValue("执行利率（%）");
				break;
			case 14:
				cell.setCellValue("放款日期");
				break;
			case 15:
				cell.setCellValue("到期日期");
				break;
			case 16:
				cell.setCellValue("结清日期");
				break;
			case 17:
				cell.setCellValue("贷款状态");
				break;
			case 18:
				cell.setCellValue("发生方式");
				break;
			default:
				break;
			}
			cell.setCellStyle(styleTitle);
			sheet.autoSizeColumn(i);
		}
	}

	/**
	 * 创建单元格样式-标题
	 * 
	 * @param workbook
	 * @return
	 */
	private CellStyle getStyleTitle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// 设置单元格字体
		Font headerFont = workbook.createFont(); // 字体
		headerFont.setFontHeightInPoints((short) 11);
		headerFont.setColor(HSSFColor.BLACK.index);
		headerFont.setFontName("宋体");
		headerFont.setBold(true);
		style.setFont(headerFont);
		// 设置单元格边框及颜色
		style.setBorderBottom((short) 2);
		style.setBorderLeft((short) 2);
		style.setBorderRight((short) 2);
		style.setBorderTop((short) 2);
		style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		return style;
	}

	/**
	 * 创建单元格样式-普通单元格
	 * 
	 * @param workbook
	 * @return
	 */
	private CellStyle getStyleCell(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// 设置单元格字体
		Font headerFont = workbook.createFont(); // 字体
		headerFont.setFontHeightInPoints((short) 11);
		headerFont.setColor(HSSFColor.BLACK.index);
		headerFont.setFontName("宋体");
		style.setFont(headerFont);
		// 设置单元格边框及颜色
		style.setBorderBottom((short) 2);
		style.setBorderLeft((short) 2);
		style.setBorderRight((short) 2);
		style.setBorderTop((short) 2);
		return style;
	}
}
