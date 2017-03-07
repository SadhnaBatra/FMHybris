/*
 * Created on Jun 6, 2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.fmo.wom.domain;

/**
 * @author amarnr85
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class UnitOfMeasureBO extends WOMBaseBO {

	private static final long serialVersionUID = 1L;

	public enum WeightUOM {
		POUND(1) {
			public String description() {
				return "lbs";
			}
		},

		KG(2) {
			public String description() {
				return "kgs";
			}
		};

		private final int weightCode;

		private WeightUOM(int i) {
			weightCode = i;

		}

		public int getWeightCode() {
			return this.weightCode;
		}

		public abstract String description();
	}

	public enum LengthUOM {
		CMS(1) {
			public String description() {
				return "cms";
			}

		},
		INCH(2) {
			public String description() {
				return "cms";
			}
		};

		private final int lengthCode;

		private LengthUOM(int i) {
			lengthCode = i;
		}

		public int getLengthCode() {
			return this.lengthCode;
		}

		public abstract String description();
	}

}