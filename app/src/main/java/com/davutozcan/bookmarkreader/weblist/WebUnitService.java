package com.davutozcan.bookmarkreader.weblist;

import com.davutozcan.bookmarkreader.domainmodel.WebUnit;

import java.util.List;

/**
 * Created by davut on 7/28/2017.
 */

public interface WebUnitService
{
    List<WebUnit> getWebSitesInfos();
    void add(String url);
    long count();
    List<WebUnit> getUnreadWebSitesInfos();
    void markRead(String url);
    List<WebUnit> getReadWebSites();
    String getSummaryFor(String url);
    List<String> getBookmarks();
    void remove(String url);
}
