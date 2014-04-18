package jp.co.humane.dbutil.sample;

public class BlockBean
{
	private int model_id = 0;
	private int id = 0;
	private String sid = null;
	private String name = null;
	private String path = null;
	private boolean basedataready = false;
	private boolean iseksb = false;
	private boolean isdyn = false;
	private int parentblock_id = 0;
	private String parentblock_sid = null;
	private int realblock_id = 0;
	private String realblock_sid = null;
	private String mask_type = null;
	private int delegateblock_id = 0;
	public int getModel_id() {
		return model_id;
	}
	public void setModel_id(int model_id) {
		this.model_id = model_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isBasedataready() {
		return basedataready;
	}
	public void setBasedataready(boolean basedataready) {
		this.basedataready = basedataready;
	}
	public boolean isIseksb() {
		return iseksb;
	}
	public void setIseksb(boolean iseksb) {
		this.iseksb = iseksb;
	}
	public boolean isIsdyn() {
		return isdyn;
	}
	public void setIsdyn(boolean isdyn) {
		this.isdyn = isdyn;
	}
	public int getParentblock_id() {
		return parentblock_id;
	}
	public void setParentblock_id(int parentblock_id) {
		this.parentblock_id = parentblock_id;
	}
	public String getParentblock_sid() {
		return parentblock_sid;
	}
	public void setParentblock_sid(String parentblock_sid) {
		this.parentblock_sid = parentblock_sid;
	}
	public int getRealblock_id() {
		return realblock_id;
	}
	public void setRealblock_id(int realblock_id) {
		this.realblock_id = realblock_id;
	}
	public String getRealblock_sid() {
		return realblock_sid;
	}
	public void setRealblock_sid(String realblock_sid) {
		this.realblock_sid = realblock_sid;
	}
	public String getMask_type() {
		return mask_type;
	}
	public void setMask_type(String mask_type) {
		this.mask_type = mask_type;
	}
	public int getDelegateblock_id() {
		return delegateblock_id;
	}
	public void setDelegateblock_id(int delegateblock_id) {
		this.delegateblock_id = delegateblock_id;
	}


}