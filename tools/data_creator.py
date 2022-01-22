import json

import requests

# base_url = "http://dev.api.basket.ccbc.finance/"
# base_url = "https://api.basket.ccbc.finance/"
base_url = "http://127.0.0.1:8080/"

coin_create_url = base_url + "api/v1/coins?clientId=token"
pool_create_url = base_url + "api/v1/pools?clientId=token"
coin_get_url = base_url + "api/v1/coins"
all_coins = requests.get(coin_get_url).json()


def generate_coin_create_body(name, address, symbol, original_symbol, symbol_image_url="", decimals=18):
    return {
        "name": name,
        "address": address,
        "symbol": symbol,
        "originalSymbol": original_symbol,
        "decimals": decimals,
        "symbolImageUrl": symbol_image_url
    }


def create_coins():
    data = [
        generate_coin_create_body("클레이튼", "0x0000000000000000000000000000000000000000", "KLAY", "KLAY",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_klay.png", 18),
        generate_coin_create_body("DAI", "0x5c74070FDeA071359b86082bd9f9b3dEaafbe32b", "KDAI", "KDAI",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("에스클레이", "0xA323d7386b671E8799dcA3582D6658FdcDcD940A", "SKLAY", "SKLAY",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("이더리움", "0x34d21b1e550D73cee41151c77F3c73359527a396", "KETH", "KETH",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_keth.png", 18),
        generate_coin_create_body("랩드비트코인", "0x16D0e1fBD024c600Ca0380A4C5D57Ee7a2eCBf9c", "KWBTC", "KWBTC",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_kwbtc.png", 8),
        generate_coin_create_body("USDT", "0xceE8FAF64bB97a73bb51E115Aa89C17FfA8dD167", "KUSDT", "KUSDT",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 6),
        generate_coin_create_body("오르빗체인", "0xFe41102f325dEaa9F303fDd9484Eb5911a7BA557", "KORC", "KORC",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("인슈어리움", "0x9657fb399847D85A9C1A234ECe9ca09D5c00f466", "ISR", "ISR",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("위드", "0x275F942985503d8CE9558f8377cC526A3aBa3566", "WIKEN", "WIKEN",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("썸씽", "0xdCd62c57182E780E23d2313C4782709Da85b9D6C", "SSX", "SSX",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("에어블록", "0x46f307B58bf05Ff089BA23799FAE0e518557f87C", "ABL", "ABL",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("레디", "0x1cD3828A2B62648dbE98d6F5748a6B1df08AC7bb", "REDI", "REDI",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("클레이스왑", "0xC6a2Ad8cC6e4A7E08FC37cC5954be07d499E7654", "KSP", "KSP",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_ksp.png", 18),
        generate_coin_create_body("아고브", "0x588C62eD9aa7367d7cd9C2A9aaAc77e44fe8221B", "AGOV", "AGOV",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("힌트", "0x4dd402A7d54eaa8147Cb6fF252AFe5BE742bDF40", "HINT", "HINT",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("클레이팜", "0xa128e62cfb454AB5B580A7385dE2F228ad7b69D1", "BEE", "BEE",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("위믹스", "0x5096dB80B21Ef45230C9E423C373f1FC9C0198dd", "WEMIX", "WEMIX",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("블루바이칼", "0x321Bc0B63EFb1e4af08Ec6D20c85D5E94dDaAa18", "BBC", "BBC",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("트라이엄프엑스", "0x0c1D7CE4982FD63b1BC77044Be1da05C995E4463", "KTRIX", "KTRIX",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("피블", "0xAfdE910130C335fA5bD5fe991053E3E0a49dcE7b", "PIB", "PIB",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("클라우드브릭", "0xC4407f7DC4B37275c9ce0F839652b393e13fF3D1", "CLBK", "CLBK",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        # generate_coin_create_body("KSPHOLD", "0xf7689072554d1e85fa9d96151F528764277d7DB2", "KSLO", "KSLO",
        #                           "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 8),
        # generate_coin_create_body("KSPLOVE", "0x065a9ddbbDd48c4189984e2F7aEDa3834bD1eAC7", "KSVE", "KSVE",
        #                           "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 8),
        generate_coin_create_body("핸디", "0x3F34671FbA493aB39FBf4Ecac2943ee62b654A88", "KHANDY", "KHANDY",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("미네랄", "0x27dCd181459bcdDC63c37baB1E404A313C0dfD79", "MNR", "MNR",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 6),
        generate_coin_create_body("트리클", "0x4B91c67A89D4c4b2A4eD9fcdE6130D7495330972", "TRCL", "TRCL",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("리플", "0x9eaeFb09fe4aABFbE6b1ca316a3c36aFC83A393F", "KXRP", "KXRP",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 6),
        generate_coin_create_body("두카토", "0x91e0d7b228A33072D9b3209cf507F78A4bD835F2", "KDUCATO", "KDUCATO",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("템코", "0x3B3b30A76d169F72A0A38AE01b0D6e0FbeE3cc2e", "TEMCO", "TEMCO",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("힙스", "0xE06b40df899b9717b4E6B50711E1dc72d08184cF", "HIBS", "HIBS",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("벨트", "0xDFe180E288158231ffA5faF183ECA3301344a51F", "KBELT", "KBELT",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("바이낸스 코인", "0x574e9c26bDA8b95D7329505b4657103710EB32eA", "KBNB", "KBNB",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("퀸비", "0x507EfA4e365FD5Def42Cb05Ae3ECB51a30321588", "KQBZ", "KQBZ",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("스트림 프로토콜", "0x49A767B188B9D782D7B0EFCD485CA3796390198e", "KSTPL", "KSTPL",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("오토팜", "0x8583063110b5d29036eceD4db1CC147e78a86a77", "KAUTO", "KAUTO",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("토카막", "0x100bC15ae8b489C771D9740ea0bb1Aea945a1f67", "KTON", "KTON",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("비블록", "0x75aD14d0360408Dc6F8163e5DFB51AAd516f4AfD", "BUZ", "BUZ",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("SIX", "0xEf82b1C6A550e730D8283E1eDD4977cd01FAF435", "SIX", "SIX",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("카이토큰", "0xe950bdcFa4d1e45472E76cf967Db93dBfc51Ba3E", "KAI", "KAI",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("카이쉐어토큰", "0x37d46C6813B121d6A27eD263AeF782081ae95434", "SKAI", "SKAI",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("라이 파이낸스", "0xb40178be0Fcf89d0051682E5512A8Bab56b9eC3E", "KRAI", "KRAI",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("터틀킹", "0x8C783809332be7734Fa782eB5139861721F77b33", "TURK", "TURK",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("러쉬 코인", "0x2Fade69ba4DCb112C530c48Fdf41fC071685CeDe", "KRUSH", "KRUSH",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("KISDT", "0x1c1187ff22Bb50A2cDCB1e1D683fCB5E8A42915f", "KISDT", "KISDT",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("sBWPM", "0xF4546E1D3aD590a3c6d178d671b3bc0e8a81e27d", "SBWPM", "SBWPM",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("PER", "0x7eeE60a000986E9efE7F5C90340738558c24317B", "PER", "PER",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("클레이파이", "0xDB116E2Dc96B4e69e3544f41b50550436579979a", "KFI", "KFI",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("아이콘", "0x8eF60f0a5A2db984431934f8659058E87CD5C70a", "KICX", "KICX",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("카이본드토큰", "0x968D93a44B3eF61168CA621a59DDc0e56583e592", "BKAI", "BKAI",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("THOUSE", "0xCAb47614B96B85538f2423BDA52342BDca7742b6", "THOUSE", "THOUSE",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("TAKLAY", "0x6Dd78DC9FcB235284E7a02CAb57Ea4DD06f568e4", "TAKLAY", "TAKLAY",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("THOUSE", "0x158BeFF8C8cDEbD64654ADD5F6A1d9937e73536c", "HOUSE", "HOUSE",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("AKLAY", "0x74BA03198FEd2b15a51AF242b9c63Faf3C8f4D34", "AKLAY", "AKLAY",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        # generate_coin_create_body("디피닉스", "0xD51C337147c8033a43F3B5ce0023382320C113Aa", "FINIX", "FINIX",
        #                           "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("랩드클레이", "0x5819b6af194A78511c79C85Ea68D2377a7e9335f", "WKLAY", "KLAY",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        # generate_coin_create_body("코잼", "0x7F223b1607171B81eBd68D22f1Ca79157Fd4A44b", "CT", "CT",
        #                           "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("인플루언서", "0x12d8A34e68f8c0333F10265A6930381657a9CC18", "INFR", "INFR",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_empty.png", 18),
        generate_coin_create_body("카이토큰 Vote", "0x44efe1ec288470276e29ac3adb632bff990e2e1f", "VKAI", "VKAI",
                                  "https://storage.googleapis.com/ccbc-app-public-asset/coin/coin_logo_klay.png", 18),
    ]

    for req_data in data:
        headers = {'Content-Type': 'application/json; charset=utf-8'}
        res = requests.post(coin_create_url, headers=headers, data=json.dumps(req_data))
        if not res.ok:
            print(req_data.get("symbol"), " request fail. " + res.reason)


def get_coin_info(symbol: str):
    for coin in all_coins:
        if coin.get("symbol").upper() == symbol.upper():
            return coin
    return None


def generate_pool_create_body(token0_symbol: str, token1_symbol: str, address,
                              dex_address="0xc6a2ad8cc6e4a7e08fc37cc5954be07d499e7654"):
    coin0 = get_coin_info(token0_symbol)
    coin1 = get_coin_info(token1_symbol)
    ksp = get_coin_info("KSP")
    coin0_address = coin0.get("address")
    coin1_address = coin1.get("address")
    if coin0_address.lower() < coin1_address.lower():
        first = coin0
        second = coin1
    else:
        first = coin1
        second = coin0
    return {
        "coin0Id": first.get("id"),
        "coin1Id": second.get("id"),
        "poolInterestCoinIds": [ksp.get("id")],
        "name": first.get("symbol") + "-" + second.get("symbol"),
        "address": address,
        "dexAddress": dex_address
    }


def create_pools():
    data = [
        generate_pool_create_body("KLAY", "KDAI", "0xa3987cf6C14F1992e8b4a9E23192Eb79dC2969b8"),
        generate_pool_create_body("KLAY", "SKLAY", "0x073FDe66B725D0eF5b54059ACa22bBFC63a929ce"),
        generate_pool_create_body("KETH", "KWBTC", "0x2A6A4b0c96cA98eB691a5ddceE3c7b7788c1a8E3"),
        generate_pool_create_body("KLAY", "KETH", "0x27F80731dDdb90C51cD934E9BD54bfF2D4E99e8a"),
        generate_pool_create_body("KETH", "KUSDT", "0x029e2A1B2bb91B66bd25027E1C211E5628dbcb93"),
        generate_pool_create_body("KLAY", "KORC", "0xe9ddb7A6994bD9cDF97CF11774A72894597D878B"),
        generate_pool_create_body("KLAY", "ISR", "0x869440673a24E3C3F18C173D8A964b2F2621245b"),
        generate_pool_create_body("KLAY", "WIKEN", "0x6119b1540AA3BeA20518f5e239f64d98EBe9AafF"),
        generate_pool_create_body("KLAY", "SSX", "0x01D71c376425b4fECCB7b8719a760110091b3eB9"),
        generate_pool_create_body("KLAY", "ABL", "0x9609861EEC1DC15756fD0F5429FB96E475790920"),
        generate_pool_create_body("KLAY", "REDI", "0x5e9BC710d817aFfA64e0fD93f3f7602E9f4dD396"),
        generate_pool_create_body("KWBTC", "KUSDT", "0x9103Beb39283dA9C49B020D6546FD7C39f9bbc5b"),
        generate_pool_create_body("KSP", "KORC", "0x6dc6bd65638B18057F7E6a2e8f136F3E77CC2038"),
        generate_pool_create_body("KLAY", "KSP", "0x34cF46c21539e03dEb26E4FA406618650766f3b9"),
        generate_pool_create_body("KLAY", "AGOV", "0x5C6795E72c47D7FA2B0C7A6446D671Aa2e381D1e"),
        generate_pool_create_body("AGOV", "HINT", "0x194896a1FBd33A13d71E0A2053d4f8129f435e31"),
        generate_pool_create_body("KLAY", "BEE", "0x1453b3cBe0167Dfac91204Eb26822fC12208F516"),
        generate_pool_create_body("KLAY", "WEMIX", "0x917EeD7ae9E7D3b0D875dd393Af93fFf3Fc301F8"),
        generate_pool_create_body("KLAY", "BBC", "0x9d9De38C473D769D76034200F122995d8b6550Ea"),
        generate_pool_create_body("KLAY", "KTRIX", "0x0B8f6200597a3b75f4d1bF0668b8ECBA2Dc77afb"),
        generate_pool_create_body("KLAY", "PIB", "0x2ecDF3088488A8e91c332B9eE86bb87D4E9cCe82"),
        generate_pool_create_body("KLAY", "KUSDT", "0xD83f1B074D81869EFf2C46C530D7308FFEC18036"),
        generate_pool_create_body("KUSDT", "KDAI", "0xc320066b25B731A11767834839Fe57f9b2186f84"),
        generate_pool_create_body("KLAY", "CLBK", "0x55A5dCC23A7A697052AB5D881530849CA0EfAD34"),
        # generate_pool_create_body("KSLO", "KSVE", "0x44FC752B7209Bdf19cC318e2191117E35b43b06D"),
        generate_pool_create_body("KLAY", "KHANDY", "0xCe28F9330658b6b4871c081e0a9A332Ae8a7d8c1"),
        generate_pool_create_body("KLAY", "MNR", "0xE641811D4a0c80d1260D4036Df54D90559b9aB54"),
        generate_pool_create_body("KLAY", "TRCL", "0x8E4e386950F6C03b25d0f9aA8Bd89C1B159E8Aee"),
        generate_pool_create_body("KORC", "KDAI", "0x587a01F81E5c078CD7C03F09f45705530fFB7B94"),
        generate_pool_create_body("KORC", "KUSDT", "0x94F390a8438b5De00B868d3aE47863Db90fB92c3"),
        generate_pool_create_body("KLAY", "KXRP", "0x86E614ef51B305C36a0608bAa57fcaaa45844D87"),
        generate_pool_create_body("KETH", "KXRP", "0x85eF87815bD7BE28BEe038Ff201dB78c7E0eD2B9"),
        generate_pool_create_body("KSP", "KXRP", "0xA06B9a38a7b4B91CB5d9B24538296bfB3B97fBf3"),
        generate_pool_create_body("KSP", "KETH", "0xa8F8f1153523eAeDce48CEc2Ddbe1Bcd483d0CD8"),
        generate_pool_create_body("KSP", "KWBTC", "0x85Fae50259EbB9a86F49BDBfb8dBaEC84a7ED5fe"),
        generate_pool_create_body("KORC", "KXRP", "0x805CB5eB7063f132cEAf56b2c7182c897a024a83"),
        generate_pool_create_body("ABL", "KORC", "0xa2867C345f9b7250Fe6BE6CCCB6360dff9F6E38c"),
        generate_pool_create_body("KSP", "KUSDT", "0xE75a6A3a800A2C5123e67e3bde911Ba761FE0705"),
        generate_pool_create_body("KSP", "KDAI", "0x64E58F35e9D4e2aB6380908905177cE150aa8608"),
        generate_pool_create_body("KXRP", "KDAI", "0x4B50d0e4F29bF5B39a6234B11753fDB3b28E76Fc"),
        generate_pool_create_body("KLAY", "KDUCATO", "0xfc4DA06cF1d201B6CB9D99265614daC4937Ad6a2"),
        generate_pool_create_body("KLAY", "TEMCO", "0x2160Db36B43cd6E2b71550D59F23d530f0578386"),
        generate_pool_create_body("KLAY", "HIBS", "0x6bF915F013DC12274ADF57e3c68fe8464ddc8B10"),
        generate_pool_create_body("KLAY", "KBELT", "0x157c39202fAE6233FEc3f8B3bCb2158200d0A863"),
        generate_pool_create_body("KLAY", "KBNB", "0xE20B9aeAcAC615Da0fdBEB05d4F75E16FA1F6B95"),
        generate_pool_create_body("KSP", "KBNB", "0x7328B85eFF28C3068F69FE662632d37D48ba227f"),
        generate_pool_create_body("KLAY", "KQBZ", "0x97575d656Ec4122d022014D8dfd1eBE189e1Ec69"),
        generate_pool_create_body("KLAY", "KSTPL", "0xfc61Fbb57Dd00765838A914e7d72A9Eceb23aD80"),
        generate_pool_create_body("KLAY", "KAUTO", "0x11Dd0Daf4C80402AD61Ea4F6B37ab60544188938"),
        generate_pool_create_body("KBELT", "KORC", "0x0f14648eD03A4172a0D186dA51b66e7e9Af6af66"),
        generate_pool_create_body("KLAY", "KTON", "0xD30339c1Edb95E69E3B5B98F230D97B12f01D844"),
        generate_pool_create_body("KLAY", "BUZ", "0x58FCF8638e8BfA38239d293960923ec7377aaB40"),
        generate_pool_create_body("KLAY", "SIX", "0x64B4ee8a878D785c9C06A18966D51A33345E5610"),
        generate_pool_create_body("KETH", "KBNB", "0x8119f0CeC72a26fE23CA1aB076137Ea5D8a19d54"),
        generate_pool_create_body("KUSDT", "KAI", "0x5787492D753D5f59365E2F98e2f18C3AE3baD6e7"),
        generate_pool_create_body("KLAY", "SKAI", "0x0734f80fbC2051E98e6C7cB37E08E067A9630c06"),
        generate_pool_create_body("KLAY", "KRAI", "0xc19fE316A03F6bCc48498b67342B29D146FED349"),
        generate_pool_create_body("KLAY", "TURK", "0x146117810f9Ddd58741cCA86a57006a65032c33f"),
        generate_pool_create_body("KSP", "SKAI", "0x6456Acb56F9eEEdb976D5d72b60fB31720155B75"),
        generate_pool_create_body("KLAY", "KRUSH", "0xDd79b37B8B90f7ce6C762120D74D5c9B85388629"),
        generate_pool_create_body("KLAY", "INFR", "0x3C2e0aC31792740DE948626FBBAf63B17fF203d8"),
        generate_pool_create_body("KLAY", "KISDT", "0xd7152a7AbCd6d7ef2b1a5423598073b32b456965"),
        generate_pool_create_body("KORC", "INFR", "0x75437D99D6905d23CBad9CedF40C3aC80C15CbE2"),
        generate_pool_create_body("KORC", "SKAI", "0x8CA4010C2a3FF730270559C96e0c94D0CAc04491"),
        generate_pool_create_body("SBWPM", "KUSDT", "0xB6eaA073881C9cac7141EF20b25A588914a367b2"),
        generate_pool_create_body("KLAY", "PER", "0x3012cc7a5a137362F97AB1fc69e61f927aC70973"),
        generate_pool_create_body("KLAY", "KFI", "0xD74D4B4d2FB186BB7F31E4000c59ADE70BbD8a23"),
        generate_pool_create_body("KETH", "KORC", "0x9E1cD9645EB2753d4D4A94786bB76cC99adb689b"),
        generate_pool_create_body("KSP", "PER", "0x7220BB49206A98AC513A98444f8e337d92c1A630"),
        generate_pool_create_body("KICX", "KETH", "0xF4048Ce0A0947F138101e4a92C44c79585d826E5"),
        generate_pool_create_body("BKAI", "KAI", "0x711094b9bc6E42F2b5Fc0e8F72833097903348d8"),
        generate_pool_create_body("KDAI", "KAI", "0x92e51BF6EC87623beb774f7356629B61005b1586"),
        generate_pool_create_body("KLAY", "THOUSE", "0xcE3f60a78c9f0073c0FC0ee095b1699Df38855dd"),
        generate_pool_create_body("KLAY", "TAKLAY", "0x338ba821329963b2F630DD4a01047B30DB0308E1"),
        generate_pool_create_body("KORC", "PER", "0x1527aC4118a56bD17A5136D73d3999C6B7f8d0D1"),
        generate_pool_create_body("KLAY", "HOUSE", "0x8ad37f3f3fB663A67c7140947F80894a973B789B"),
        generate_pool_create_body("KLAY", "AKLAY", "0xE74C8D8137541C0EE2C471cdAF4DCf03C383Cd22"),
        generate_pool_create_body("PER", "KUSDT", "0xe55f6452C5B04108dcC2E75aE31D6Ca141a4F9B7")
    ]
    for req_data in data:
        headers = {'Content-Type': 'application/json; charset=utf-8'}
        res = requests.post(pool_create_url, headers=headers, data=json.dumps(req_data))
        if not res.ok:
            print(req_data.get("name"), " request fail. " + res.reason)


if __name__ == '__main__':
    create_coins()
    create_pools()
