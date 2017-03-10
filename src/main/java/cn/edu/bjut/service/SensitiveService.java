package cn.edu.bjut.service;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;

/**
 * Created by Administrator on 2017/3/10.
 */
@Service
public class SensitiveService implements InitializingBean{
    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);
    private final String DEFALT_REPALCEMENT = "***";
    private class TrieNode{
        //标示是否是关键词
        private boolean end = false;
        //key标示下一个字符，value标示下一个节点
        Map<Character,TrieNode> subNodes = new HashMap<Character,TrieNode>();
        //将节点设置为结束标志
        private void setEnd(boolean end){
            this.end = end;
        }
        //读取当前节点是否为结束关键字
        private boolean isEnd(){
            return end;
        }
        //加入关键字
        private void addSubNode(Character key,TrieNode node){
            subNodes.put(key,node);
        }
        //获取子关键字
        private TrieNode getSubNode(Character key){
            return subNodes.get(key);
        }
    }
    //根节点
    TrieNode root = new TrieNode();
    /**
     *@Author: hanxiao
     *@Description:判断是否是一个合法字符
     *@Date: 22:52 2017/3/10
    */
    private boolean isSymbol(char c) {
        int ic = (int) c;
        // 0x2E80-0x9FFF 东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }
    /**
     *@Author: hanxiao
     *@Description:添加关键字
     *@Date: 22:51 2017/3/10
    */
    private void addWords(String lineTxt){
        TrieNode curNode = root;
        for(int i=0;i<lineTxt.length();++i){
            Character c = lineTxt.charAt(i);
            if(isSymbol(c))
                continue;
            TrieNode node = curNode.getSubNode(c);
            if(node == null){
                node = new TrieNode();
                curNode.addSubNode(c,node);
            }
            curNode = node;
            if(i == lineTxt.length() - 1){
                curNode.setEnd(true);
            }
        }
    }
    /**
     *@Author: hanxiao
     *@Description:过滤敏感词
     *@Date: 23:00 2017/3/10
    */
    public String filter(String text){
        if(StringUtils.isBlank(text)){
            return text;
        }
        String replacement = DEFALT_REPALCEMENT;
        StringBuilder sb = new StringBuilder();

        TrieNode tempNode = root;
        int begin = 0;
        int position = 0;

        while (position < text.length()){
            Character c = text.charAt(position);
            if(isSymbol(c)){
                if(tempNode == root){
                    ++begin;
                    sb.append(c);
                }
                ++position;
                continue;
            }
            tempNode = tempNode.getSubNode(c);
            if(tempNode == null){
                // 以begin开始的字符串不存在敏感词
                sb.append(text.charAt(begin));
                // 跳到下一个字符开始测试
                position = begin + 1;
                begin = position;
                // 回到树初始节点
                tempNode = root;
            }else if(tempNode.isEnd()){
                // 发现敏感词， 从begin到position的位置用replacement替换掉
                sb.append(replacement);
                position = position + 1;
                begin = position;
                tempNode = root;
            }else{
                ++position;
            }
        }
        //sb.append(text.charAt(begin));

        return sb.toString();
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        try{
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader read = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null){
                lineTxt = lineTxt.trim();
                addWords(lineTxt);
            }
            read.close();
        }catch (Exception e){
            logger.error("写入敏感词失败" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SensitiveService sensitiveService = new SensitiveService();
        sensitiveService.addWords("色情");
        System.out.println(sensitiveService.filter("你好，你需要色情光盘么ffff"));
    }
}
