using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Xml;
using System.Collections.Generic;
using System.Net;
using System.IO;

namespace MultiConsultCRM.Web.Resources
{
    public partial class WeatherProxy : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            string _location = Request.QueryString["location"];
            string _metric = Request.QueryString["metric"];

            //string _url = string.Format("http://rainmeter.accu-weather.com/widget/rainmeter/weather-data.asp?location={0}&metric={1}", _location, _metric);
			string _url = string.Format("http://wwwa.accuweather.com/adcbin/forecastfox/weather_data.asp?location={0}&metric={1}", _location, _metric);
			
            string _xml = DownloadWebPage(_url);

            XmlDocument _xmlDocument = new XmlDocument();
            _xmlDocument.LoadXml(_xml);

            XmlNamespaceManager _mgr = new XmlNamespaceManager(_xmlDocument.NameTable);
            _mgr.AddNamespace("pf", _xmlDocument.DocumentElement.NamespaceURI);

            Weather _weather = new Weather();

            _weather.city = 
                _xmlDocument.SelectSingleNode("/pf:adc_database/pf:local/pf:city", _mgr).InnerText;
            _weather.curr_temp = Convert.ToInt32(
                _xmlDocument.SelectSingleNode("/pf:adc_database/pf:currentconditions/pf:temperature", _mgr).InnerText);
            _weather.curr_text =
                _xmlDocument.SelectSingleNode("/pf:adc_database/pf:currentconditions/pf:weathertext", _mgr).InnerText;
            _weather.curr_icon = Convert.ToInt32(
                _xmlDocument.SelectSingleNode("/pf:adc_database/pf:currentconditions/pf:weathericon", _mgr).InnerText);

            XmlNodeList _xmlNodeList = _xmlDocument.SelectNodes("/pf:adc_database/pf:forecast/pf:day", _mgr);
            int _day = _xmlNodeList.Count;
            int i = 0;
            foreach (XmlNode _dayItem in _xmlNodeList)
            {
                Forecast _forecast = new Forecast();

                _forecast.day_date = _dayItem["obsdate"].InnerXml;
                _forecast.day_text = _dayItem.SelectSingleNode("pf:daytime", _mgr)["txtshort"].InnerXml;
                _forecast.day_icon =
                    Convert.ToInt32(_dayItem.SelectSingleNode("pf:daytime", _mgr)["weathericon"].InnerXml);
                _forecast.day_htemp =
                    Convert.ToInt32(_dayItem.SelectSingleNode("pf:daytime", _mgr)["hightemperature"].InnerXml);
                _forecast.day_ltemp =
                    Convert.ToInt32(_dayItem.SelectSingleNode("pf:daytime", _mgr)["lowtemperature"].InnerXml);

                _weather.forecast.Add(_forecast);

                i++;
				// 5 day forecast
				if (i == 5) break;
            }

            Response.Write(Newtonsoft.Json.JsonConvert.SerializeObject(_weather));
        }

        /// <summary>
        /// Returns the content of a given web adress as string.
        /// </summary>
        /// <param name="Url">URL of the webpage</param>
        /// <returns>Website content</returns>
        public string DownloadWebPage(string Url)
        {
            // Open a connection
            HttpWebRequest WebRequestObject = (HttpWebRequest)HttpWebRequest.Create(Url);

            // You can also specify additional header values like 
            // the user agent or the referer:
            WebRequestObject.UserAgent = ".NET Framework/2.0";
            WebRequestObject.Referer = "http://www.example.com/";

            // Request response:
            WebResponse Response = WebRequestObject.GetResponse();

            // Open data stream:
            Stream WebStream = Response.GetResponseStream();

            // Create reader object:
            StreamReader Reader = new StreamReader(WebStream);

            // Read the entire stream content:
            string PageContent = Reader.ReadToEnd();

            // Cleanup
            Reader.Close();
            WebStream.Close();
            Response.Close();

            return PageContent;
        }
    }

    public class Weather
    {
        public string city;
        public int curr_temp;
        public string curr_text;
        public int curr_icon;

        public List<Forecast> forecast = new List<Forecast>();
    }

    public class Forecast
    {
        public string day_date;
        public string day_text;
        public int day_icon;
        public int day_htemp;
        public int day_ltemp;
    }
}
