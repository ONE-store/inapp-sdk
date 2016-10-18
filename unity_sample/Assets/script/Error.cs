using System;
using System.Text;

namespace IapError 
{
	[Serializable]
	public class Error {
		public string requestId;
		public string errorCode;
		public string errorMessage;

		public string ToString() 
		{
			StringBuilder sb = new StringBuilder ("[Error]\n");
			sb.Append ("requestId: " + requestId + "\n");
			sb.Append ("errorCode: " + errorCode + "\n");
			sb.Append ("errorMessage: " + errorMessage + "\n");
			return sb.ToString ();
		}

	}
}