using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;

namespace IapResponse
{
	
	[Serializable]
	public class Status 
	{
		public string code;
		public string message;

		public string ToString() 
		{
			StringBuilder sb = new StringBuilder ("[Status]\n");
			sb.Append ("code: " + code + "\n");
			sb.Append ("message: " + message + "\n");
			return sb.ToString ();
		}
	}

	[Serializable]
	public class Product 
	{
		public string appid;
		public string id;
		public string name;
		public string type;
		public string kind;
		public int validity;
		public double price;
		public string startDate;
		public string endDate;
		public bool purchasability;
		public Status status;

		public string ToString() 
		{
			StringBuilder sb = new StringBuilder ("[Product]\n");
			sb.Append ("appid: " + appid + "\n");
			sb.Append ("id: " + id + "\n");
			sb.Append ("name: " + name + "\n");
			sb.Append ("type: " + type + "\n");
			sb.Append ("kind: " + kind + "\n");
			sb.Append ("validity: " + validity + "\n");
			sb.Append ("price: " + price + "\n");
			sb.Append ("startDate: " + startDate + "\n");
			sb.Append ("endDate: " + endDate + "\n");
			sb.Append ("purchasability: " + purchasability + "\n");
			if (status != null) {
				sb.Append (status.ToString ());
			}
			return sb.ToString ();
		}
	}

	[Serializable]
	public class Result 
	{
		public string message;
		public string code;
		public string txid;
		public string receipt;
		public int count;
		public List<Product> product;

		public string ToString() 
		{
			StringBuilder sb = new StringBuilder ("[Result]\n");
			sb.Append ("message: " + message + "\n");
			sb.Append ("code: " + code + "\n");
			sb.Append ("txid: " + txid + "\n");
			sb.Append ("receipt: " + receipt + "\n");
			sb.Append ("count: " + count + "\n");
			if (product != null) 
			{
				foreach (Product p in product) 
				{
					sb.Append (p.ToString ());
				}
			}
			return sb.ToString ();
		}
	}

	[Serializable]
	public class Response 
	{
		public string api_version;
		public string identifier;
		public string method;
		public Result result;

		public string ToString() 
		{
			StringBuilder sb = new StringBuilder ("[Response]\n");
			sb.Append ("api_version: " + api_version + "\n");
			sb.Append ("identifier: " + identifier + "\n");
			sb.Append ("method: " + method + "\n");
			if (result != null) 
			{
				sb.Append ("\n" + result.ToString ());
			}
			return sb.ToString ();
		}
	}
}