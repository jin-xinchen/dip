/** 
@description  
[
    {
        "ASSET_TYPE_SYSID": "8", "name": "Pole"
    },
    {
        "ASSET_TYPE_SYSID": "7",  "name": "Transformer"
    },
    {
        "ASSET_TYPE_SYSID": "6",  "name": "Padmount Transformer"
    },
    {
        "ASSET_TYPE_SYSID": "5",  "name": "Restaurant"
    }
]
*/

export interface AssetType{
    ASSETTYPE_SYSID:string;//  by getListOfAssetTypes()
  //ASSET_TYPE_SYSID:string|number;
  name:string;
}
/** 
 *@description array 
  [{"CODE": 1,"CODE_VALUE": "BOOL","DESCRIPTION": "Boolean( 1/0; T/F)"}] 
*@example
//             $scope.QuestionType = [
//     {"CODE": 1,"CODE_VALUE": "BOOL","DESCRIPTION": "Boolean( 1/0; T/F)"}
// //    ,
// //    {"CODE": 4,"CODE_VALUE": "CHAR","DESCRIPTION": "Char (VARCHAR/CHAR 128)"},
// //    {"CODE": 14,"CODE_VALUE": "CMT","DESCRIPTION": "Comment"},
// //    {"CODE": 5,"CODE_VALUE": "DATE","DESCRIPTION": "Date"},
// //    {"CODE": 3,"CODE_VALUE": "DEC","DESCRIPTION": "Decimal (NUMBER(6,0))"},
// //    {"CODE": 23,"CODE_VALUE": "FREE","DESCRIPTION": "Free Form Text"},
// //    {"CODE": 2,"CODE_VALUE": "NUM","DESCRIPTION": "Number"},
// //    {"CODE": 26,"CODE_VALUE": "PERC","DESCRIPTION": "Percentage (%)"},
// //    {"CODE": 25,"CODE_VALUE": "LIST","DESCRIPTION": "PopUp List"},
// //    {"CODE": 21,"CODE_VALUE": "RNGE","DESCRIPTION": "Range of Values (Custom)"},
// //    {"CODE": 11,"CODE_VALUE": 12345,"DESCRIPTION": "Range Value (1-5)"},
// //    {"CODE": 10,"CODE_VALUE": "ABCDE","DESCRIPTION": "Range Value (A-E)"},
// //    {"CODE": 22,"CODE_VALUE": "SLDR","DESCRIPTION": "Slider"},
// //    {"CODE": 8,"CODE_VALUE": "YNNA","DESCRIPTION": "Yes/No and N/A value option"},
// //    {"CODE": 20,"CODE_VALUE": "YN","DESCRIPTION": "Yes/No Only"}
// ];  

*/
export interface QuestionType{
  CODE:number;
  CODE_VALUE:string;
  DESCRIPTION:string;
}