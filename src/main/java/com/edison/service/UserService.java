package com.edison.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edison.db.entity.User;

/**
 * @author Edison
 * �û�������*/
@Service
public class UserService {
	/**
	 * �û�ע��*/
	public boolean userRegister(User user){ //��ʵ�������ʹ��Context���������ģ��������԰���Ϣ�����ݳ�ȥ
		//�û��Ϸ��Լ��
		String name=user.getUserName();
		System.out.println("�û���"+name+"������ע��");
		
		//��ѯ���ݿ⿴�Ƿ��Ѵ���
		boolean exist=false;
		if(exist==true){
			System.out.println("�û��Ѵ���");
			return false;
		}else{
			//TODO ���û��������ݿ�
			System.out.println("�û���ע��ɹ�");
		}
		
		return true;
	}
}
