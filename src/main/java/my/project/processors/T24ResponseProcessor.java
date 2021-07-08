/*
 * Copyright 2016 Red Hat, Inc.
 * <p>
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */
package my.project.processors;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

@Component
public class T24ResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		String response = "";
		String incmngXml = exchange.getIn().getBody(String.class);
		
		System.out.println("==========================REQUEST======"+ incmngXml);
		
		String value = this.getUnsanitizedTagValue(incmngXml,"criteriaValue");
        //response  = getErrorOK();
		if(getRandomInteger(1, 0) == 0) {
			//logger.info("0 - OK");
			response = getResponseOK(value);
		}else{
			//logger.info("1 - ZEROS");
			response = getResponseZeros(value);
		}

		exchange.getOut().setBody(response);
		exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "application/xml");
		exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
	}

	
	private String getResponseOK(String Acc) {
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<?xml version=\"1.0\" ?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns5:ChannelAccountBalanceResponse xmlns:ns5=\"http://sprintel.com/AccountBalStmt\" xmlns:ns4=\"http://sprintel.com/CHANNELACCTBAL\" xmlns:ns3=\"http://sprintel.com/SPRINTACCTMINISTMT\" xmlns:ns2=\"http://sprintel.com/SPRINTCHANNELACCTDATA\"><Status><successIndicator>Success</successIndicator></Status><SPRINTCHANNELACCTBALType><ns4:gSPRINTCHANNELACCTBALDetailType><ns4:mSPRINTCHANNELACCTBALDetailType><ns4:AcctNumber>");
		xmlBuilder.append(Acc);
		xmlBuilder.append("</ns4:AcctNumber><ns4:Currency>KES</ns4:Currency><ns4:UnclearedBalance>0.00</ns4:UnclearedBalance><ns4:AccountBalance>1021.34</ns4:AccountBalance><ns4:LimitAmount>0.00</ns4:LimitAmount><ns4:InactiveMarker></ns4:InactiveMarker><ns4:PostingRestrict></ns4:PostingRestrict><ns4:WorkingBalance>1021.34</ns4:WorkingBalance><ns4:LockedAmount>0.00</ns4:LockedAmount><ns4:MinimumBalance>0.00</ns4:MinimumBalance></ns4:mSPRINTCHANNELACCTBALDetailType></ns4:gSPRINTCHANNELACCTBALDetailType></SPRINTCHANNELACCTBALType></ns5:ChannelAccountBalanceResponse></S:Body></S:Envelope>");

		return xmlBuilder.toString();
	}

	
	private String getResponseZeros(String Acc) {
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<?xml version=\"1.0\" ?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns5:ChannelAccountBalanceResponse xmlns:ns5=\"http://sprintel.com/AccountBalStmt\" xmlns:ns4=\"http://sprintel.com/CHANNELACCTBAL\" xmlns:ns3=\"http://sprintel.com/SPRINTACCTMINISTMT\" xmlns:ns2=\"http://sprintel.com/SPRINTCHANNELACCTDATA\"><Status><successIndicator>Success</successIndicator></Status><SPRINTCHANNELACCTBALType><ns4:gSPRINTCHANNELACCTBALDetailType><ns4:mSPRINTCHANNELACCTBALDetailType><ns4:AcctNumber>");
		xmlBuilder.append(Acc);
		xmlBuilder.append("</ns4:AcctNumber><ns4:Currency>KES</ns4:Currency><ns4:UnclearedBalance>0.00</ns4:UnclearedBalance><ns4:AccountBalance>1021.34</ns4:AccountBalance><ns4:LimitAmount>0.00</ns4:LimitAmount><ns4:InactiveMarker></ns4:InactiveMarker><ns4:PostingRestrict></ns4:PostingRestrict><ns4:WorkingBalance>1021.34</ns4:WorkingBalance><ns4:LockedAmount>0.00</ns4:LockedAmount><ns4:MinimumBalance>0.00</ns4:MinimumBalance></ns4:mSPRINTCHANNELACCTBALDetailType></ns4:gSPRINTCHANNELACCTBALDetailType></SPRINTCHANNELACCTBALType></ns5:ChannelAccountBalanceResponse></S:Body></S:Envelope>");

		return xmlBuilder.toString();
	}
	
	/*
	private String getResponseOK(String Acc) {
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<?xml version=\"1.0\" ?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns5:KCBChannelAccountBalanceResponse xmlns:ns5=\"http://temenos.com/T24KCBAccountBalStmt\" xmlns:ns4=\"http://temenos.com/KCBCHANNELACCTBAL\" xmlns:ns3=\"http://temenos.com/KCBACCTMINISTMT\" xmlns:ns2=\"http://temenos.com/KCBCHANNELACCTDATA\"><Status><successIndicator>Success</successIndicator></Status><KCBCHANNELACCTBALType><ns4:gKCBCHANNELACCTBALDetailType><ns4:mKCBCHANNELACCTBALDetailType><ns4:AcctNumber>");
		xmlBuilder.append(Acc);
		xmlBuilder.append("</ns4:AcctNumber><ns4:Currency>KES</ns4:Currency><ns4:UnclearedBalance>0.00</ns4:UnclearedBalance><ns4:AccountBalance>1021.34</ns4:AccountBalance><ns4:LimitAmount>0.00</ns4:LimitAmount><ns4:InactiveMarker></ns4:InactiveMarker><ns4:PostingRestrict></ns4:PostingRestrict><ns4:WorkingBalance>1021.34</ns4:WorkingBalance><ns4:LockedAmount>0.00</ns4:LockedAmount><ns4:MinimumBalance>0.00</ns4:MinimumBalance></ns4:mKCBCHANNELACCTBALDetailType></ns4:gKCBCHANNELACCTBALDetailType></KCBCHANNELACCTBALType></ns5:KCBChannelAccountBalanceResponse></S:Body></S:Envelope>");

		return xmlBuilder.toString();
	}

	
	private String getResponseZeros(String Acc) {
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<?xml version=\"1.0\" ?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns5:KCBChannelAccountBalanceResponse xmlns:ns5=\"http://temenos.com/T24KCBAccountBalStmt\" xmlns:ns4=\"http://temenos.com/KCBCHANNELACCTBAL\" xmlns:ns3=\"http://temenos.com/KCBACCTMINISTMT\" xmlns:ns2=\"http://temenos.com/KCBCHANNELACCTDATA\"><Status><successIndicator>Success</successIndicator></Status><KCBCHANNELACCTBALType><ns4:gKCBCHANNELACCTBALDetailType><ns4:mKCBCHANNELACCTBALDetailType><ns4:AcctNumber>");
		xmlBuilder.append(Acc);
		xmlBuilder.append("</ns4:AcctNumber><ns4:Currency>KES</ns4:Currency><ns4:UnclearedBalance>0.00</ns4:UnclearedBalance><ns4:AccountBalance>1021.34</ns4:AccountBalance><ns4:LimitAmount>0.00</ns4:LimitAmount><ns4:InactiveMarker></ns4:InactiveMarker><ns4:PostingRestrict></ns4:PostingRestrict><ns4:WorkingBalance>1021.34</ns4:WorkingBalance><ns4:LockedAmount>0.00</ns4:LockedAmount><ns4:MinimumBalance>0.00</ns4:MinimumBalance></ns4:mKCBCHANNELACCTBALDetailType></ns4:gKCBCHANNELACCTBALDetailType></KCBCHANNELACCTBALType></ns5:KCBChannelAccountBalanceResponse></S:Body></S:Envelope>");

		return xmlBuilder.toString();
	}
	*/

	public String getUnsanitizedTagValue(String xml, String tagName) {
		Document doc = null;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource src = new InputSource();
			src.setCharacterStream(new StringReader(xml));
			doc = builder.parse(src);
		} catch (Exception e) {

		}
		return doc != null ? doc.getElementsByTagName(tagName).item(0) != null ? doc.getElementsByTagName(tagName).item(0).getTextContent() : "" : "";
	}

	/* * returns random integer between minimum and maximum range */ 
	public static int getRandomInteger(int maximum, int minimum){ 
		return ((int) (Math.random()*((maximum - minimum)+1))) + minimum; 
	}
}
