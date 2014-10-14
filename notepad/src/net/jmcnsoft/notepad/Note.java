package net.jmcnsoft.notepad;

import java.io.Serializable;

public class Note
  implements Serializable
{
  public static final String CONTENT = "content";
  public static final String CREATED_TIME = "created_time";
  public static final String ID = "id";
  public static final String MODIFIED_TIME = "modified_time";
  public static final String TITLE = "title";
  private static final long serialVersionUID = -8732589068827777529L;
  private String content;
  private Long createdTime;
  private Integer id;
  private Long modifiedTime;
  private String title;
  
  public String getContent()
  {
    return this.content;
  }
  
  public Long getCreatedTime()
  {
    return this.createdTime;
  }
  
  public Integer getId()
  {
    return this.id;
  }
  
  public Long getModifiedTime()
  {
    return this.modifiedTime;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setContent(String paramString)
  {
    this.content = paramString;
  }
  
  public void setCreatedTime(Long paramLong)
  {
    this.createdTime = paramLong;
  }
  
  public void setId(Integer paramInteger)
  {
    this.id = paramInteger;
  }
  
  public void setModifiedTime(Long paramLong)
  {
    this.modifiedTime = paramLong;
  }
  
  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
}


/* Location:           D:\tmp\notepadV1.0_dex2jar1.jar
 * Qualified Name:     cn.jerry.mouse.notepad.Note
 * JD-Core Version:    0.7.0.1
 */