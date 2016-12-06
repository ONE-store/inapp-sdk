using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;

namespace IapVerifyReceipt 
{
	
	[Serializable]
	public class Product {
		public string log_time;
		public string appid;
		public string product_id;
		public double charge_amount;
		public string tid;
		public string detail_pname;
		public string bp_info;
		public string tcash_flag;

		public string ToString() 
		{
			StringBuilder sb = new StringBuilder ("[Product]\n");
			sb.Append ("log_time: " + log_time + "\n");
			sb.Append ("appid: " + appid + "\n");
			sb.Append ("product_id: " + product_id + "\n");
			sb.Append ("charge_amount: " + charge_amount + "\n");
			sb.Append ("tid: " + tid + "\n");
			sb.Append ("detail_pname: " + detail_pname + "\n");
			sb.Append ("bp_info: " + bp_info + "\n");
			sb.Append ("tcash_flag: " + tcash_flag + "\n");
			return sb.ToString ();
		}

	}
		
	[Serializable]
	public class VerifyReceipt
	{
		public int status;
		public string detail;
		public string message;
		public int count;
		public List<Product> product;

		public string ToString() 
		{
			StringBuilder sb = new StringBuilder ("[VerifyReceipt]\n");
			sb.Append ("status: " + status + "\n");
			sb.Append ("detail: " + detail + "\n");
			sb.Append ("message: " + message + "\n");
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
}