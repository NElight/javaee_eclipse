'''
Created on 2018年12月24日

@author: NElight
'''
#!/usr/bin/python
#-*- coding:utf-8 -*-
from multiprocessing import Pool
import argparse
import json
import requests
from builtins import print

# param username=18781047495&password=000000
login_url = "http://www.wzsg91.com/index.php?s=api/public/login"
# param uid=23016&app_token=ff1de93b813927e426a2f12196422bb5
index_url = "http://www.wzsg91.com/index.php?s=api/user/index"
# param uid=23021&app_token=15d0efbb8fc9d2c9cee8efa7d3f40839
battle_url = "http://www.wzsg91.com/index.php?s=api/game/battle"
# param hid=1&xnum=1&uid=23021&app_token=15d0efbb8fc9d2c9cee8efa7d3f40839
add_url = "http://www.wzsg91.com/index.php?s=api/game/add"
# param uid=23016&app_token=985df1d6fb0da5d24a1085f0048376a6
logout_url = "http://www.wzsg91.com/index.php?s=api/public/logout"

header = {"User-Agent":"Mozilla/5.0 (Linux; Android 4.1.1; Nexus 7 Build/JRO03D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Safari/535.19"}


def main(infile, process, isStartThread, special):
    if isStartThread:
        pass
    else:
        startProcess(infile, process, special)

def startProcess(infile, process, special):
    p = Pool(process)
    for i in range(process):
        p.apply_async(run,args=(i,infile,special))
    p.close()
    p.join()


def run(name, infile, special):
    with open(infile, "r") as f:
        for line in f:
            print("ok")
            lineList = line.strip().split("\t")
            username = lineList[0]
            password = "000000"
            if special:
                password = username
            if len(lineList) == 2:
                password = lineList[1]
            wzsg_run(username, password)

def wzsg_run(username, password):
    status, uid, token = wzsg_login(username, password)
    if not status:
        return
    hid, xnum = wzsg_index(uid, token)
    battle_status = wzsg_battle(uid, token)
#     if not battle_status:
#         return
    add_status = wzsg_add(hid, xnum, uid, token)
#     if not add_status:
#         return
    logout_status = wzsg_logout(uid, token)


def wzsg_login(username, password):
    status = False
    user_uid = ''
    app_token= ''
    a = requests.post(login_url, {'username':username, 'password':password}, headers = header);
    login_return_str = a.text
    login_dict = json.loads(login_return_str)
    status = login_dict['success']
    if (status == True):
        user_uid = login_dict['data']['uid']
        app_token = login_dict['data']['app_token']
        print("username:" + username + "\t" + "password:" + password + "\t" + "success:" + "success");
    else:
        print("username:" + username + "\t" + "password:" + password + "\t" + "error:" + login_dict['message'])
    return (status, user_uid, app_token)

def wzsg_index(uid, token):
    index_return_str = requests.post(index_url, {'uid':uid, 'app_token':token}, headers = header).text
    index_dict = json.loads(index_return_str)
    hid = '1'
    xnum = '1'
    return (hid, xnum)

def wzsg_battle(uid, token):
    status = False
    
    battle_return_str = requests.post(battle_url, {'uid':uid, 'app_token':token}, headers = header).text
    battle_dict = json.loads(battle_return_str)
    status = battle_dict['success']
    if status:
        print("uid:" + uid + "\t" + "token:" + token + "success:" + "success")
    else:
        print("uid:" + uid + "\t" + "token:" + token + "error:" + battle_dict['message'])
    
    return status
    

def wzsg_add(hid, xnum, uid, token):
    add_return_str = requests.post(add_url, {'hid':hid, 'xnum':xnum, 'uid':uid, 'app_token':token}, headers = header).text
    add_dict = json.loads(add_return_str)
    status = add_dict['success']
    if status:
        print("uid:" + uid + "\t" + "token:" + token + "success:" + "success")
    else:
        print("uid:" + uid + "\t" + "token:" + token + "error:" + add_dict['message'])
    
    
    return status

def wzsg_logout(uid, token):
    logout_return_str = requests.post(logout_url, {'uid':uid, 'app_token':token}, headers = header).text
    logout_dict = json.loads(logout_return_str)
    status = logout_dict['success']
    if status:
        print("uid:" + uid + "\t" + "token:" + token + "success:" + "success")
    else:
        print("uid:" + uid + "\t" + "token:" + token + "error:" + logout_dict['message'])
    return status


def parse_args():
    parser = argparse.ArgumentParser(description="this is auto wzsg")
    parser.add_argument("-i", "--infile", required=True,help="file include all tel nun")
    parser.add_argument("-p", "--process", type = int, help="the process will be create by p")
    parser.add_argument("--startThread", action="store_true", default = False, help = "true will start thread"
                                                                                      "false will start process")
    parser.add_argument("-s", "--special", action="store_true", default=False, help = "True password is 000000"
                                                                                    "false password is tel")
    args = parser.parse_args()
    return args

if __name__ == "__main__":
    args = parse_args()
    main(args.infile, args.process, args.startThread, args.special)
#     wzsg_login("15183863497", "000000")