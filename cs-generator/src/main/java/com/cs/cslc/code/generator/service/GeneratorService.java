/*
 *MIT License
 *
 *Copyright (c) 2019 chenshuai cs4380@163.com
 *
 *Permission is hereby granted, free of charge, to any person obtaining a copy
 *of this software and associated documentation files (the "Software"), to deal
 *in the Software without restriction, including without limitation the rights
 *to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *copies of the Software, and to permit persons to whom the Software is
 *furnished to do so, subject to the following conditions:
 *
 *The above copyright notice and this permission notice shall be included in all
 *copies or substantial portions of the Software.
 *
 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *SOFTWARE.
 */

package com.cs.cslc.code.generator.service;

import com.cs.cslc.code.generator.mapper.GeneratorMapper;
import com.cs.cslc.code.generator.utils.GeneratorUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * GeneratorService .
 *
 * @author cs
 * @version 1.0
 * @date 2019-09-30 14:23
 * @description
 */
@Service
public class GeneratorService {

    @Autowired
    private GeneratorMapper generatorMapper;

    public List<Map<String, Object>> queryList(Map<String, Object> map) {
        int offset = Integer.parseInt(map.get("offset").toString());
        int limit = Integer.parseInt(map.get("limit").toString());
        map.put("offset", offset);
        map.put("limit", limit);
        return generatorMapper.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return generatorMapper.queryTotal(map);
    }

    public Map<String, String> queryTable(String tableName) {
        return generatorMapper.queryTable(tableName);
    }

    public List<Map<String, String>> queryColumns(String tableName) {
        return generatorMapper.queryColumns(tableName);
    }

    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for (String tableName : tableNames) {
            //查询表信息
            Map<String, String> table = queryTable(tableName);
            //查询列信息
            List<Map<String, String>> columns = queryColumns(tableName);
            //生成代码
            GeneratorUtils.generatorCode(table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
}